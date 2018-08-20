# MedragLogistics
T-systems exam project

### Content
  - [Synopsis](#synopsis)
  - [Used technologies](#used-technologies)
  - [Additional features](#additional-features)
  - [Database scheme](#database-scheme)
  - [Application modules](#application-modules)
  - [User interface](#user-interface)
  - [Buisness logic](#buisness-logic)
  - [Entity list](#entity-list)
  - [Junit tests](#junit-tests)

### Synopsis
Medrag Logistics is a web application for governing a trucking company. It assumes segrigation of duties among four types of users with separated credentails:
  1. Resourse managers are responsible for company's property and staff.
  2. Logisticans' goal is cargo routing, including truck and drivers assigning.
  3. Warehouse workers take orders, implement cargo works and give away cargoes to recepients.
  4. Drivers drive trucks.
The main goal of this scheme is provide a high quality trucking service.

### Used technologies
  - Spring framework
  - Jackson
  - MySQL 5.6
  - Flyway
  - Hibernate 5
  - ModelMapper
  - RabbitMQ
  - Google directions API
  - Google maps API
  - Tomcat 9.0
  - Wildfly 13.0
  - Maven 3.5
  - JSP
  - EJB
  - JSF
  
### Additional features
Using google directions api and google maps api for routing trucks and managing trucking.
  
### Database scheme
  - **city** - table with city information.
  - **truck** - table represents truck. Contains information about it's current city and destination city as foreign keys to *id* fields of 'city' table.
  - **driver** - table of drivers. Among other thigs it has an information about driver's current city and destination city as well. Also indicates driver's truck, if it's not null.
  - **user** - contains user data. Also if user is a driver, denotes a relevant driver, basing on it's *id*.
  - **customer** - table with information about customer.
  - **orderr** - order table. Contains information about it's owner (foreign key to customer's id).
  - **cargo** - cargoes' table. All information about cargoes is there, including order (foreign key to orderr table) and current truck, if it's not null (also foreign key to relevant table).
  - **waypoint** - don't represents any real world objects. Means an action of cargo works instead. Can be *LOAD*, *UNLOAD* or just *CHECK* type. It's fields *City_id*, *Truck_id* and *Cargo_id* reference to relevant tables and use their *id* fields as foreign keys.
  
### Application modules
Application consists of 4 modules with their own zones of responsibility:
  - **core** - core modul. Includes entities, data transfer objects, dao tier, service tier.
  - **database** - consists of database settings files.
  - **web** - contains controllers and views.
  - **MedragLogistics** - parent modul for that three above.
  
### User interface
Templates and frameworks, used in UI:
  - [Bootstrap](https://getbootstrap.com/docs/4.1/getting-started/introduction/)
  - [SB-Admin 2](https://github.com/BlackrockDigital/startbootstrap-sb-admin-2)
  - [DataTables](https://datatables.net/)
  
### Buisness logic
Service tier consists of two types of services:
  - **Dto services** - named in honor of related entities.
  - **Handlers** - these are who really rules the app.
#### Handler services:
  - **CityHandlingService** - handles complecated requests, related with cities
  - **DirectionsService** - used for cooperating with google directions api
  - **DriverHandlerService** - handles complecated requests, related with drivers
  - **EmployeeIdentifierService** - needed for creating unique personal numpers for employees and generating passwords
  - **IndexService** - indexes new orders and cargoes
  - **MailService** - sends emails
  - **OrderHandlingService** - handles complecated requests, related with orders
  - **RabbitService** - MQ messenger
  - **RouteService** - routing trucks and assigning drivers buisness logic
  - **SecurityService** - spring security service
  - **UserDetailsService** - another spring security service
  - **WatcherService** - works with Watcher App
  
### Entity list
  - **city** - city representation
  - **truck** - representation of truck. Contains information about it's current city and destination city.
  - **driver** - driver representation. Among other thigs it has an information about driver's current city and destination city.
  - **user** - contains user data. Also if user is a driver, denotes a relevant driver as it's field.
  - **orderr** - order representation. Contains information about it's owner as a *customer* field.
  - **cargo** - representation of cargo. All information about cargoes is there, including order and current truck.
  - **waypoint** - don't represents any real world object. Means an action of cargo works instead.
  
### Junit tests
  - **dao.impl** - test relevant dao.
  - **service.dto.impl** - test revelant services
  - **CityHandlingServiceImplTest** - tests methods of *CityHandlingServiceImpl*
  - **DirectionsServiceImplTest** - tests methods of *DirectionsServiceImpl*
  - **DriverHandlerServiceImplTest** - tests methods of *DriverHandlerServiceImpl*
  - **EmployeeIdentifierServiceImplTest** - tests methods of *EmployeeIdentifierServiceImpl*
  - **IndexServiceImplTest** - tests methods of *IndexServiceImpl*
  - **MailServiceImplTest** - tests methods of *MailServiceImpl*
  - **OrderHandlingServiceImplTest** - tests methods of *OrderHandlingServiceImpl*
  - **RabbitServiceImplTest** - tests methods of *RabbitServiceImpl*
  - **RouteServiceImplTest** - tests methods of *RouteServiceImpl*
  - **SecurityServiceImplTest** - tests methods of *SecurityServiceImpl*
  - **UserDetailsServiceImplTest** - tests methods of *UserDetailsServiceImpl*
  - **WatcherServiceImplTest** - tests methods of *WatcherServiceImpl*
  
       

  
  
  
  
  
