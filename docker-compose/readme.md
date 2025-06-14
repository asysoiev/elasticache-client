## Prepare
1. Copy .env.sample to .env
2. Update values in .env according to environment

## redis-cluster-dc.yml 

Contains example of Redis cluster with 3 main nodes and 1 replica for each node.  
Cluster requires at least 6 nodes  
### To run all services, execute the command:  
`docker-compose -f redis-cluster-dc.yml up -d`  
### To stop, execute the command:  
`docker-compose -f redis-cluster-dc.yml down`  
### For connecting to redis-cli inside docker container, execute the command: 
redis-cli -p ${port}  
e.g. for redis-node-1 the command is:  
redis-cli -p 7001
### Redis Insight
Starts redisinsight container - web dashboard for redis cluster.  
All redis nodes are added.

## redis-standalone-dc.yml

Example of redis standalone setup.  
To run all services, execute the command:  
`docker-compose -f redis-standalone-dc.yml up -d`