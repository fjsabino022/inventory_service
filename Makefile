SHELL=/bin/bash
PROJECT_NAME={{{ service_name }}}
REPO_NAME={{{ repo_name }}}
GIT_COMMIT?=latest
APP_ENV?=dev

all: test

test:
	mvn test
	curl -fsSL "http://y8s.g.int/repos/$(REPO_NAME)/validate?rev=$(GIT_COMMIT)"

package:
	mvn package -DskipTests -DskipTests=true -Djacoco.skip=true -Dmaven.test.skip=true

release: test package
	mvn deploy

deploy:
	kubectl apply -f "http://y8s.g.int/repos/$(REPO_NAME)?env=$(APP_ENV)&rev=$(GIT_COMMIT)"

clean:
	mvn clean
	kubectl delete ns $(PROJECT_NAME) --cascade=true --grace-period=5 || true
