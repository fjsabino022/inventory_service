# {{{ repo_name }}}

> Production ready service.

## Features

- CI/CD pipeline - everything to go from dev to prod
	- pipeline.yml with a service, cron job and PR jobs
	- deployment templates
	- routing (service & ingress)
	- makefile

- Starter pack base - everything needed for production readiness
	- https://github.com/goeuro/spring-boot-starters
	- development: docker, sonarqube, gitinfo for release info, spring devtools for hot class reloading
	- production: structured json logs, metrics (micrometer+dropwizard), actuator, swagger

- Application configuration
	- See `ops/values.yaml` for some default application configs
	- See `ops/values/<dev|qa|...>.yaml` where they are overridden per environment
	- See `deployment.yaml` where these configs are converted into env variables
