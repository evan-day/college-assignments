# College Assignments

This is a repository for projects I worked on in college that I felt like sharing!

## First Year - Coffee Shop

This was a project that was done before we were introduced to any form of object oriented programming. It's a
menu driven system that uses loyalty cards to determine when individuals are entitled to free coffees.

## Third Year - Palindrome

This was my first project in C and is a simple checker for determining if a number is in fact a palindrome. The checker can be
controlled with a set of keyboard commands.

## Third Year - Distributed File Sharing System (Sockets)

This is a project which uses object oriented principles with Java sockets, Threads, some design patterns and JavaFX for a GUI.
It functions as a file sharing system where multiple clients can connect to a server to upload and download files. Clients can
download mp3 files and play them locally on their machines. To see the code in action, you will have to ensure the pathways are
updated for your machine.

## Third Year - Distributed File Sharing System (RMI)

This is essentially the same as the Sockets file sharing system, however it uses Java RMI in place of sockets.

## Fourth Year - Frontend Frameworks (Portfolio)

This was an assignment where we had to create a portfolio website for ourselves, using a framework that we chose ourselves. For the project, I decided to use ReactJS as my framework of choice and used Material UI as a design basis for the portfolio site.

## Fourth Year - Real Time Systems (Assignment One and Two)

In Real Time Systems, we did a variety of tasks which revolved generally around Process creation, sensors, actuators, signals, etc. The first assignment was a collection of tasks we had to undertake. The second assignment revolved around creating a subset of a real time system for a warehouse. I only managed to implement a small portion of my overall plan, which revolved around the loading and unloading bays of the warehouse. Nevertheless, I was happy with what I managed to implement given my circumstances!

## Fourth Year - Scalable Microservices (Project)

In Scalable Microservices, we were essentially given free reign to develop a project, so long as we had some form of inter service communication. I developed a set of services that leveraged Stripe's API in order to mimic a sort of online payment system. One could create and delete customers, add and delete card details, charge customers and subscribe them to a recurring billing plan. It was all implemented in NodeJS while using Pug as a templating engine for the front end. The services were running on Kubernetes and used an Ingress in order to expose endpoints that could be called by the required services. As with Real Time Systems, certain functionality was not able to be fully implemented. But I gained a thorough understanding of building REST APIs with NodeJS and successfully running those APIs in a Kubernetes cluster.

## Fourth Year - Final Year Project

The final year project folders contain the various pieces of software that worked together to implement the goals of my final year project. The aim was to create an Automated CI/CD Creation Platform, where the entire process for spinning up a Platform for CI/CD could be automated. This was accomplished with the various controllers that make up the project. Each individual folder has a slightly more detailed description of what it does, but just something to remember is that it is very much a **proof of concept** and might not follow best practices or fully take advantage of certain tools that were in use.
