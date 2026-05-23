pipeline {
    agent any

    environment {
        // Reemplazamos por tu ruta exacta de NVM descubierta por consola
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
        stage('3. Pruebas de API (Postman)') {
            steps {
                sh 'newman run "TECNOLOGICA TALLER_RAA1_U2.postman_collection.json"'
            }
        }
        stage('4. Pruebas de Interfaz (Selenium)') {
            steps {
                sh 'mvn test -Dtest=FormularioSeleniumTest -Dchrome.headless=true'
            }
        }
    }
}