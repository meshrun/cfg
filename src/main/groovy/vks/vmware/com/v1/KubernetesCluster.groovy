package vks.vmware.com.v1

import k8s.K8sResource

class KubernetesCluster extends K8sResource {
  @Override
  protected String apiVersion() {
    return "vks.vmware.com/v1"
  }

  KubernetesCluster(String name, @DelegatesTo(TypedKubernetesCluster.class) Closure body) {
    super(name, body)
  }

  @Override
  void prepare() {
    super.prepare()
    // direct access as we override setProperty and getProperty
    def workers = spec?.topology?.workers
    if (workers?.class_) {
      Object val = workers?.class_
      workers?.remove("class_")
      workers["class"] = val
    }
  }
}
