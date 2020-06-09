pipeline {
    agent any
    environment {
        IMAGE_NAME = 'htc-data-manager'
        REGISTRY_NAME = 'registry-htc.dilau.kz'
        SERVICE_NAME = 'htc-data-manager'
        SERVICE_PORT = '6002'
        registry = 'https://registry-htc.dilau.kz'
        registryCredential = 'registry-user'
    }
    tools {
        maven 'mvn-3.6.3'
    }
    stages {
        stage('Code analysis') {
            steps {
                withSonarQubeEnv('sonarqube') {
                    sh 'mvn clean package sonar:sonar -Dmaven.test.skip=true'
                }
            }
        }

        stage('Building image') {
            steps {
                script {
                    docker.withRegistry(registry, registryCredential) {
                        def customImage = docker.build("${env.IMAGE_NAME}:${env.BUILD_ID}")
                        customImage.push()
                    }
                }
            }
        }

        stage('Deploy') {
            steps {
                echo 'Deploying'
                script{
                    docker.withRegistry(registry, registryCredential) {
                        try {
                            sh '''
                                  SERVICES=$(docker service ls --filter name=${SERVICE_NAME} --quiet | wc -l)
                                  if [ "$SERVICES" -eq 0 ]; then
                                    docker service create --with-registry-auth --replicas 1 \
                                    --network consul-nw --network datamanager-db-nw --network service-nw --network traefik-nw --network logstash-nw \
                                    --constraint node.role==worker \
                                    --env consul_host=consul \
                                    --env logstash_url=logstash-log:5044 \
                                    --env log_level=INFO \
                                    --env ES_JAVA_OPTS=-Djava.awt.headless=true \
                                    --name ${SERVICE_NAME} \
                                    --label traefik.frontend.rule="Host:dm-htc.dilau.kz" \
                                    --label traefik.enable=true \
                                    --label traefik.port=8080 \
                                    --label traefik.tags=traefik-nw \
                                    --label traefik.docker.network=traefik-nw \
                                    --label traefik.redirectorservice.frontend.entryPoints=http \
                                    --label traefik.redirectorservice.frontend.redirect.entryPoint=https \
                                    --label traefik.webservice.frontend.entryPoints=https \
                                    --update-order start-first --stop-grace-period 60s \
                                    -p ${SERVICE_PORT}:8080 ${REGISTRY_NAME}/${IMAGE_NAME}:${BUILD_ID}
                                  else
                                    docker service update --with-registry-auth --update-order start-first --stop-grace-period 60s --image ${REGISTRY_NAME}/${IMAGE_NAME}:${BUILD_ID} ${SERVICE_NAME}
                                  fi
                                  '''
                        } catch(e) {
                            sh "docker service update --rollback ${SERVICE_NAME}"
                            error "Service update failed in production"
                        }
                    }
                }
            }
        }
    }

    post {
        success {
            echo 'All good, we passed tests.'
        }
        failure {
            sh "docker service update --rollback ${SERVICE_NAME}"
        }
    }
}
