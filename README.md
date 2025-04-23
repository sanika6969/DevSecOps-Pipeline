
---

# Production-Ready DevSecOps Pipeline

**Secure Kubernetes Deployment, Automation, and Monitoring on On-Premises vSphere**

---

![DevSecOps block diagram drawio (1)](https://github.com/user-attachments/assets/81aaa600-0452-4e74-a8c1-89d1c3429e0b)


## Overview

This project sets up a complete DevSecOps pipeline using:

- Rancher for Kubernetes management  
- Jenkins for CI/CD  
- Trivy for container scanning  
- SonarQube for code analysis  
- Nexus for artifact storage  
- Prometheus and Grafana for monitoring  
- All hosted on VMware vSphere

---

## Architecture

- 5 VMs for core tools: SonarQube, Nexus, Jenkins, Prometheus, Grafana  
- Kubernetes Cluster: 1 master and 2 worker nodes via Rancher RKE  
- Secure CI/CD namespace using Kubernetes Network Policies

---

## Key Features

1. **CI/CD Pipeline**
   - Jenkinsfile with Maven, SonarQube, Nexus, and Trivy integration  
   - GitHub integration  
   - Email notifications via SMTP  

2. **Security**
   - Kubernetes network policies  
   - Trivy for image vulnerability scanning  

3. **Monitoring**
   - Prometheus for metrics collection  
   - Grafana for dashboards and visualization  

---

## Quick Setup Steps

1. Create 5 Ubuntu VMs (minimum 4GB RAM, 20GB disk)  
2. Install Docker on SonarQube and Nexus VMs  
3. Deploy SonarQube and Nexus using Docker  
4. Install Rancher and provision the Kubernetes cluster  
5. Apply Kubernetes network policies  
6. Install Jenkins, Trivy, and Maven  
7. Configure Jenkins CI/CD pipeline  
8. Set up Prometheus and Grafana for monitoring  

---

## Resources Included

- Jenkinsfile  
- pom.xml  
- Kubernetes YAMLs for all deployments  

---

## Monitoring Dashboards

Monitor and visualize:
- CI/CD pipeline performance  
- System and VM metrics  
- Kubernetes cluster health  

---

## Security and Compliance

The pipeline integrates static analysis, image scanning, and network policies to ensure secure and compliant software delivery.

---
