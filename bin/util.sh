#create computer
curl -d '{"computerId":"10", "model":"model888", "creationDate":"2018-05-08"}' \
-H "Content-Type: application/json" -X POST http://localhost:8080/computers

#start docker container for redis
sudo docker run --name redis-cache-spring-ex -d -p 6380:6379 redis

echo "{}" > .docker/config.json

#linux - allow non root user to start containers
#https://docs.docker.com/install/linux/linux-postinstall/#manage-docker-as-a-non-root-user
#Create the docker group.
sudo groupadd docker
#Add your user to the docker group.
sudo usermod -aG docker $USER

