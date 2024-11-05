# Deployment

## Pre-requisites

### Dependencies

You need an existing MySQL database to run the application. Create a database named `qvog-bot` and run the `init.sql` under `src/main/resources/init.sql` to initialize the tables.

It is recommended that you run QVoG Bot in our given docker container. The container is pre-configured with all required dependencies.

### Mail Configuration

QVoG Bot will deliver an email to the author when the scan completes. This is done through [Mailtrap](https://mailtrap.io/). So you need to create a Mailtrap account and follow its instructions to set up your domain.

## Start the Bot

### Pull the Docker Image

The environment is pre-configured in the Docker image. Pull it in the production server.

```bash
docker pull registry.cn-wulanchabu.aliyuncs.com/dockmoil/qvog-bot:1.0
```

### Prepare the Working Directory

Git QVoG Bot a directory to work in as it mounts some directories to the container. After you find the place, copy `assets/init.sh` to the working directory and run it. It will create a `bot/` directory under the working directory and initialize necessary tools.

### Publish the Bot

There is an automatic deployment script `deploy.ps1` in this repo. You can run it to deploy the bot. First, you need to run `maven package` and `npm run build` (in frontend) to prepare the files to deploy.

```powershell
.\deploy.ps1
```

> Before you run this script, create a `.env` file containing the SSH profile and deployment path on the server. The deployment path is the absolute path of the working directory. 

Then, there is some work to be done manually.

In the deployment working directory, create a `conf.d/` directory, and copy the `assets/conf.d/qvog.conf` into it. Then, modify the `qvog.conf` to fit your environment.

### Run the Bot

Finally, you are ready to activate the bot. Copy `assets/run.sh` to your server, and set all required environments. If you want to have a try, use `--rm` option. Otherwise, replace `--rm` with `-d` to run it in background.

## Customization

This section describes how to customize the deployment of the application.

### Launch Script

The launch script of the container is located at `/root/startup/launch.sh`. The script is as follows:

```bash
#!/bin/bash

# launch Nginx
echo launching Nginx
nginx

# launch apache gremlin server
cd /opt/apache-tinkerpop-gremlin-server-3.7.0
bash bin/gremlin-server.sh start conf/gremlin-server-neo4j.yaml

# prepare Python environment
source /opt/miniconda3/etc/profile.d/conda.sh
conda activate qvog
conda env list

# launch bot
java -Duser.timezone=Asia/Shanghai -jar /bot/application.jar --spring.profiles.active=prod
```

To override the default launch script, you can mount your custom `/root/startup/` directory to the container. The custom
launch script should still be named `launch.sh`.
