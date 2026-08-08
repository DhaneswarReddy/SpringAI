#SpringAI DOC
https://docs.spring.io/spring-ai/reference/api/chat/openai-chat.html


#To use Bedrock for Open-ai services
- Create a IAM user in your AWS account.
- Get the access key and secret key for the IAM user.   
- Login to your AWS account using the AWS CLI: (with above user credentials created)
  aws configure


-----------------------------------------------------------------------------------------------------

#Deploying
----------
- Initially using Dockerfile to generate the img.
- To manage multiple images/services we have moved to docker-compose file
- docker-compose contains multiple services (ollama, spring-ai: generated based upon the Dockerfile)
- Inside ollama container we can check list of the model; If our req model is not present then we pull
      ollama pull llama3.2:3b

- spring-ai container communicate with ollama container by a docker network drivers
- Docker Compose:
   Compose automatically creates a network for all services.
   Services can communicate using their service names. (refer docker-compose.yml file) 

- springai_default → this is the Docker Compose network created for your SpringAI project.
- Both your spring-ai_container and ollama_container are automatically attached to this network when you run docker compose up.
- That’s why they can talk to each other using service names.   


- used to publish our img in docker hub
  docker tag spring-ai sathyabama40110529/spring-ai:latest ; docker tag <imp> <username>/<img>:<version>
  docker push sathyabama40110529/spring-ai:latest ; docker push <username>/<img>:<version>