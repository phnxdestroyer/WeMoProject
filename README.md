# WeMoProject

<img src="https://github.com/phnxdestroyer/WeMoProject/blob/master/markdown/markdown.png" width="200">


## Purpose
Connect to the home autmation device, WeMo Insight Switch by Belkin. Provide a application that could be a better implementation of Belkin's Wemo App.

  
  [User Interface Inspiration](https://github.com/phnxdestroyer/WeMoProject/blob/master/markdown/design%20interface.png)
  
## Design: Architecture


<img src="https://github.com/phnxdestroyer/WeMoProject/blob/master/markdown/architecture.png" width="350" alt = "Model-View-Presenter">

* View is a layer that displays data and reacts to user actions. On Android, this could be an Activity, a Fragment, an android.view.View or a Dialog.
* Model is a data access layer such as database API or remote server API.
* Presenter is a layer that provides View with data from Model. Presenter also handles background tasks. 

#### Why Model View Presenter?
* Views independent from our data source.
* Easier to create unit tests.
* Reusability of Code
* Modular code, increasing flexibility
For this project, we were able to work on different layers of the application at the same time.

## User Interface: RecyclerView
<img src="https://github.com/phnxdestroyer/WeMoProject/blob/master/markdown/final%20screenshot%201.png" width="350">

The RecyclerView displays currently connected WeMos in the network.
Each WeMo is given a power switch to switch on/off the WeMo.
An extendable view is available in order to view more details on certain WeMos
These details include: Current mW, Average W, Time last on, Time it has been on, and a GraphView detailing Current mW to Time on.


## Backend: UPNP Implementation
Universal Plug and Play (UPnP) is a set of networking protocols that permits networked devices to discover each other's presence on the network and establish functional network services for data sharing, communications, and entertainment. 
* Configured to look for only Wemo Devices
* Receive a list of properties and available actions from the device
*	Subscribe to to changes of properties of a device.
*	Invoke actions on the device. Such as “GetBinaryState” and “SetBinaryState”
<img src="https://github.com/phnxdestroyer/WeMoProject/blob/master/markdown/action%20services.png" width="300">

### Relationship with the Presenter layer

The presenter uses these methods to request data from the UPNP service. 
The presenter establishes itself as the handler any callbacks received from the backend. For example adding a new device.
The updates are then given to the view to display information.

Methods:
```java
public void searchForWemo();
public void stopListeningToDevices();
public void subcribeToBinaryState();
public String getPowerState();
public void togglePowerOnOff();
public WemoSubscriptionCallback subscribeInsightParams();
```
## TODO:
Allow users to set a description for each individual WeMo device.
Save images to distinguish different devices
Set timers or times when the device should switch off.
Expand application to other smart home devices
Format the time in the graphview to be better understood
