package app;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import chatapp.ChatApp;

@ApplicationPath(ChatApp.Rest.BASE)
public class App extends Application {

}
