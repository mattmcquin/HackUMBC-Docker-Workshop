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
| `docker build <options> <pathWithDockerfile>` | Builds a Docker image from the given Dockerfile. |
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
| `EXPOSE <port1> <port2> ...` | *Documents* ports to expose. This notifies a user to use the `-p <port>` flag when running the image. |

# Demo!

For the demo we will be building a very basic Dockerized application. Our application has two components:
1. `demo-backend` - A basic Java (with Spring Boot) app that queries the Twitter API for a given keyword. It parses the tweets, tokenizes them, counts the occurrences, and removes some boring words (he, she, it, they, a, an, and, the, etc.). A REST endpoint is exposed which takes in a given keyword and returns the processed response in the form of a Map of word to occurrence strength.
1. `demo-ui` - A basic JavaScript (React) app that allows the user to input a word, submit a query to our demo-backend application, and generate a wordcloud from the Tweets that resulted from the Twitter query.

### Prerequisites 
You must have the following installed to follow along in the demo:
1. Docker
1. Git
1. Maven
1. Node.js

### Let's get started!

1. Start by cloning the demo .git project: 
`git clone https://github.com/mattmcquin/HackUMBC-Docker-Workshop.git`
2. We first need to build our backend Java application and generate our .jar file. We can use Maven do that! Navigate into the demo-backend package containing our Java code, then run:
`mvn package`
3. Now that the backend's .jar files exist, we can build the Docker image. This will involve using OpenJDK for a base image, copying our .jar files into the Docker container, and then starting up the application. You can view the Dockerfile to see all the steps that will happen. To build the image, we run (from within the demo-backend package): 
`docker build -t demo-backend:1.0 .`
4. Now, if you run a `docker images`, you should see both the OpenJDK base image that was downloaded from Docker Hub, as well as the demo-backend image that we just built. Awesome!
5. To start up our new image in Docker, we run: 
`docker run -d -p 8080:8080 demo-backend:1.0`
6. We can check the logs of the newly started container with:
`docker logs -f <containerId>` (make sure you insert the ID that Docker started the container with)
7. Ctrl + C to get out of the logs
8. If you run a `docker ps`, you should see your backend application running
9. NOTE: This backend requirest secret API keys, which were redacted in Github. Your backend will start, but will not be able to query the Twitter API unless you sign up for a Twitter developer account and input your own keys! Sorry!
10. Do a `docker inspect <containerId>` and take note of the `IPAddress` assigned to your container. You will need this in the next step.
11. Navigate to the demo-ui package, and open the file `App.js`
12. In order for Docker to be able to correctly query the backend we have running, we need to insert the IP address we saw in step 10 in the fetch call around line 29. This address may be correct, but correct it if it is not. Make sure to keep the port number there, and save the file!
13. If you do not have the Twitter API access, in `App.js`, comment out the part of the code that is documented to be for API use only, and uncomment the code that should be used if API acess does not exist. 
13. Back to the terminal, in the demo-ui package, you should see yet another Dockerfile. Feel free to view this file to see the steps we are going to use to generate the UI Docker image. 
14. Still in the demo-ui package, we build the UI Docker image with: 
`docker build -t demo-ui:1.0 .` (note that this may take a while as `npm install` runs)
15. We can now run our UI image with:
`docker run -d -p 3001:3000 demo-ui:1.0`
16. We can check our images with:
`docker images`
17. Again, we can check the logs here and ensure a clean startup with:
`docker logs -f <containerId>`
18. Now, in our browser, we should be able to navigate to `localhost:3001` and view our word cloud!

