# HackUMBC-Docker-Workshop

Hello everyone! Welcome to the Docker workshop here at the UMBC Hackathon! Hopefully you find this workshop helpful in boostrapping and deploying applications using Docker. There is limited time, so lets get started:

### What is Docker?
Docker is a containerization technology used to package and run applications anywhere. Many of you may be familiar with virtual machines (VMs). Docker works in a similar way, though there are some major differences between running applications in a VM and running them in Docker. The main difference between them is that unlike the VM, a docker container may or may not have an Operating System running in it. The Docker container runtime allows the container to share the host OS, which improves performance and drastically reduces the number of resources needed for the infrastructure itself.

### Why use Docker?
Docker makes our applications portable, faster, and easier to deploy into cloud-based environments. It also allows us to re-use functionality at an application level. For example, you can tell Docker to start a MySQL database with a single command - all without even having MySQL installed! More on this later. For a hackathon, Docker can drastically cut down infrastructure set up times since there are millions of preconfigured Docker images to chose from. 

# Installing Docker
