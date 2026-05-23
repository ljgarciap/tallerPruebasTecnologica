pipeline {
    agent any

    environment {
        PATH = "/Users/lgarcia/.nvm/versions/node/v23.10.0/bin:/opt/homebrew/bin:/usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin:${env.PATH}"
    }

    stages {
        stage('1. Clonar Repositorio') {
            steps {
                git 'https://github.com/ljgarciap/tallerPruebasTecnologica'
            }
        }

        stage('2. Pruebas Unitarias (JUnit)') {
            steps {
                sh 'mvn test -Dtest=UsuarioControllerUnitTest'
            }
        }

        stage('3. Levantar Servidor Spring Boot') {
            steps {
                // Arranca el servidor web en segundo plano para que los endpoints queden vivos
                sh 'mvn spring-boot:start'
            }
        }

        stage('4. Pruebas de API (Postman)') {
            steps {
                // Ahora que el puerto 8080 está abierto, Newman no fallará
                sh 'newman run "TECNOLOGICA TALLER_RAA1_U2.postman_collection.json"'
            }
        }

        stage('5. Pruebas de Interfaz (Selenium)') {
            steps {
                sh 'mvn test -Dtest=FormularioSeleniumTest -Dchrome.headless=true'
            }
        }
    }

    post {
        always {
            // Buenas prácticas: Pase lo que pase, apagamos el servidor para liberar el puerto 8080
            sh 'mvn spring-boot:stop || true'
        }
    }
}