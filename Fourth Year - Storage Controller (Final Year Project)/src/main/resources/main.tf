# data "terraform_remote_state" "main" {
#   backend = "s3"
#   config {
#     bucket = "S3_BUCKET_NAME"
#     key    = "S3_KEY"
#     region = "AWS_REGION"
#     access_key = "AWS_ACCESS_KEY"
#     secret_key = "AWS_SECRET_KEY"
#     dynamodb_table = "DYNAMODB_TABLE"
#   }
# }
# Configure the DigitalOcean Provider
provider "digitalocean" {
  token = "${var.digitalOceanToken}"
}

resource "digitalocean_kubernetes_cluster" "final-year-project-cluster" {
  name    = "final-year-project-cluster"
  region  = "ams3"
  version = "1.13.5-do.0"
  tags    = ["test"]

  node_pool {
    name       = "worker-pool"
    size       = "c-4"
    node_count = 2
  }
}

resource "local_file" "test-kubeconfig" {
    content     = "${digitalocean_kubernetes_cluster.final-year-project-cluster.kube_config.0.raw_config}"
    filename = "${path.module}/kubeconfig.yaml"
}
