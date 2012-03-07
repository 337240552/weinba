package com.weinba;


public class Settings {
    public static final String USERNAME = "Mayday";
    public static final String URL = "http://www.weinba.com/xmlrpc/";
    public static final String PASSWORD = "132300d820596851f890c8903b07bafe";
    public static final String NICK_NAME = "Mayday";
    
    protected static Connector mConnector = null;
    
    
    public static void setConnector(Connector paramConnector)
    {
        mConnector = paramConnector;
    }
    
    public static Connector getConnector()
    {
      return mConnector;
    }
}
