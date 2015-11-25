# core
This folder contains the core Java and Maven based implementation of OpenInfRA. The project page can be found [here](http://www.b-tu.de/openinfra/).

# Installation

To run OpenInfRA different prerequisites must be complied. At this point we will give a broad overview about the necessary components and their configuration.
OpenInfRA consists of the following parts:

## Application
The application is written in Java and must be compiled with Java 7. It must be packed into a _war_ file and run on a server e.g. Apache Tomcat. There are only a few handles necessary to configure the application. The main [configuration file](openinfra_core/src/main/resources/de/btu/openinfra/backend/properties/OpenInfRA.properties) must be adapted to the current needs. The different configuration parameters are commented and should need no further explanations. The prime configurations are the database connection and file path properties. These must be set correctly to run the application.

## Database
The [database](https://github.com/OpenInfRA/database) is necessary to provide a data storage for OpenInfRA. Further instructions can be found in the appropriated repositoriy.

## Solr
To make use of Solr in OpenInfRA two prerequisites must be complied. First the [server](https://github.com/OpenInfRA/solr_server) itself must be installed. Second the [core definition](https://github.com/OpenInfRA/solr_core) must be installed in the specified file path. Further instructions can be found in the appropriated repositories.

## ImageMagick
OpenInfRA provides a file upload. This upload requires [ImageMagick](http://www.imagemagick.org) to generate and convert different image representations.
