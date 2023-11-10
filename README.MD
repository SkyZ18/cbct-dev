# cbct - Car Build Customer Tool
#### This Tool is used to accept client requests and is capeable to order new products for the Storage.

---
## Install and Setup

> [!IMPORTANT]
> Install Docker, Git, Node, Maven and an IDE of your choice

### First Step
You need to have node installed. To check this run:
```
node -v
```
If not installed, install it with the link below or install it via Homebrew on Mac.
https://nodejs.org/de/download

Mac download:
```
brew install node
```
---
### Docker
To run the Webapp in a Container, you will need Docker. You can install Docker-Desktop from the official Docker Site.

## Download the project

### Git
Clone the project via this command into a Folder of your choice:
```
git clone https://github.com/SkyZ18/cbct-dev.git
```

### IDE
Open the Api in your IDE, and run a Maven clean && package to setup the project.
After you cd into the Frontend folder and run:
```
npm install
ng build
```

### Setup not in IDE
Verify that Maven is installed:
```
mvn -v
```
If thats not the case, install it on **https://www.javatpoint.com/how-to-install-maven** and follow the guide.

On Mac you can run:
```
brew install maven
```

Now just run a Maven clean && package and follow the above Steps for the Frontend.

### Docker-containers
Now that the Project is Set up, you can init the containers for the Application.
Simply run:
```
docker-compose up
```
Now you can check in Docker-desktop if the containers are running. To call the Frontend, simply click this link **http://localhost:80/**

