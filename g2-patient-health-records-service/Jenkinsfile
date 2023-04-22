pipeline{

agent any

stages{

stage('Checkout'){

steps{

git branch: "main", url: 'https://github.com/chiranjiv09/g2-patient-health-records-service.git'

}

}

stage('Build'){

steps{

sh 'chmod a+x mvnw'

sh './mvnw clean package -DskipTests=true'

}

post{

always{

archiveArtifacts 'target/*.jar'

}

}

}

stage('DockerBuild') {

steps {

sh 'docker build -t services/g2-patient-health-records-service:latest .'

}

}

stage('Login') {

steps {

sh 'echo dockerhub123 | docker login -u 8962311939 --password-stdin'

}

}

stage('Push') {

steps {

sh 'docker push services/g2-patient-health-records-service'

}

}

}

post {

always {

sh 'docker logout'

}

}

}