# HackUMBC-Docker-Workshop

Hello everyone! Welcome to the Docker workshop here at the UMBC Hackathon! Hopefully you find this workshop helpful in boostrapping and deploying applications using Docker. There is limited time, so lets get started:

### What is Docker?
Docker is a containerization technology used to package and run applications anywhere. Many of you may be familiar with virtual machines (VMs). Docker works in a similar way, though there are some major differences between running applications in a VM and running them in Docker. The main difference between them is that unlike the VM, a docker container may or may not have an Operating System running in it. The Docker container runtime allows the container to share the host OS, which improves performance and drastically reduces the number of resources needed for the infrastructure itself.

### Why use Docker?
Docker makes our applications portable, faster, and easier to deploy into cloud-based environments. It also allows us to re-use functionality at an application level. For example, you can tell Docker to start a MySQL database with a single command - all without even having MySQL installed! More on this later. For a hackathon, Docker can drastically cut down infrastructure set up times since there are millions of preconfigured Docker images to chose from. 

# Installing Docker
Follow the installation instructions for your particular OS: https://docs.docker.com/v17.12/docker-for-mac/install/

NOTE: If you are running Windows 10 Home, you cannot use Docker for Windows. You must use the Docker Toolbox. Docker Toolbox installation can be found [here](https://docs.docker.com/v17.12/toolbox/toolbox_install_windows/). Windows 10 Pro, Enterprise, and Student users should not have a problem using the Docker for Windows installation.

# Testing Docker Installation
To test your Docker installation, you can run `docker run hello-world`. You should see a message generated as a result of Docker downloading the hello-world image. It should explain the steps Docker followed to get the hello-world application up and running. If you are a Linux user and you need `sudo` in order to run Docker commands, you may have missed a step. Try running `sudo usermod -aG docker $USER` and then logging out and back in. If that did not work, try restarting your computer/VM and trying again. 

# Docker Hub
The Docker Hub is a repository where you can find millions of docker images. Images like Fedora, MongoDB, Ubuntu, Redis, PostgreSQL, MySQL, RabbitMQ, and many more! By default, Docker is configured to search for docker images here. When we executed `docker run hello-world`, Docker first looked for an image named hello-world locally. When it couldn't find it, it reached out to the Docker Hub and downloaded it. How convenient!

We can get a little more involved with our Docker-ing: Try running `docker run -it ubuntu`. Notice anything? It started a container running Ubuntu and sent you the bash shell! Ctrl + D will exit and terminate the container. 

# A Few Essential Docker Commands

| Command | Description |
| ------- | ----------- |
| `docker ps` | Shows your running containers. |
| `docker images` | Shows you all images available locally. |
| `docker rmi <image-name>` | Removes an image. |
| `docker pull <image-name>` | Downloads an image from the Docker Hub (or a configured repository) without trying to run it. |
| `docker run -it <image-name> <command>` | Starts a container with the given image name, in interactive mode. Use this for things like shells where you would like to mantain an open connection. |
| `docker run -d <image-name>` | Starts a container in the background. The container will stay alive until it terminates itself or you specifically tell Docker to terminate it. |
| `docker run -p <port> <image-name>` | Starts a container with the given port exposed. |
| `docker exec  <image> <command>` | Executes a command on the container. E.g. `docker exec -it <image> bash` gives a bash shell. |
| `docker start/stop/pause <containerId>` | Starts/stops/pauses an existing container. |
| `docker tag <image-name> <new-image>` | Re-tags the given image with a new name/tag. Old image still exists. |
| `docker logs -f <containerId>` | Opens and follows (-f) the logs on the existing container. |
| `docker inspect <someId>` | Displays basic info about Docker objects, given some ID. It could be a container ID, an image, a netowrk, etc.  |

# Dockerfiles
We have started existing Docker images, but how do we make our own? The short answer: a Dockerfile. Dockerfiles are simple in concept, and don't necessarily have to be complicated (though many complicated applications have large Dockerfiles). An example Dockerfile can be seen HERE. Essentially, this file is a set of instructions Docker uses to package, setup, transfer files, and execute your application. There are a few main keywords to note:

| Keyword/Options        | Description |
| ---------------------- | ----------- |
| `FROM <base-image>:<version>` | Specifies a base image to build on - millions exist on the Docker Hub. |
| `WORKDIR <directory>` | Sets the working directory within the container - kind of like using "cd" or "dir". |
| `RUN <command>` | Runs a command in the container. Maybe you want to do an "apt-get install" for a particular package. |
| `VOLUME <hostPath>:<dockerPath>` | Creates a volume on the host to persist data, so when the container exits the data still exists! |
| `COPY <hostPath> <dockerPath>` | Copies the file(s)/directory from the host into the container at the specified path. |
| `CMD ["command", "arg1", ... ]` | The main command to kick off your container. E.g. `CMD ["npm", "start"]`. |
| `EPOSE <port1> <port2> ...` | *Documents* ports to expose. This notifies a user to use the `-p <port>` flag when running the image. |

