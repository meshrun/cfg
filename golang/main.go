package main

import(
	"C"

	"bytes"
	"fmt"
	"unsafe"
	"github.com/hashicorp/hcl/hcl/printer"
	jsonParser "github.com/hashicorp/hcl/json/parser"
)

//export json_to_hcl
func json_to_hcl(input *C.char, size C.int) *C.char {
	ast, err := jsonParser.Parse(C.GoBytes(unsafe.Pointer(input), size))
	if err != nil {
		return C.CString(fmt.Sprintf("%v", err))
	}

	var buf bytes.Buffer
	err = printer.Fprint(&buf, ast)
	if err != nil {
		return C.CString(fmt.Sprintf("%v", err))
	}
	buf.WriteString("\n")
	buf.WriteByte(0)

	return (*C.char)(C.CBytes(buf.Bytes()))
}

func main() {
}