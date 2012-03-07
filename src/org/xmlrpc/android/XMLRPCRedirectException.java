package org.xmlrpc.android;

public class XMLRPCRedirectException extends Exception
{
  private static final long serialVersionUID = 1L;
  protected String m_sRedirectUrl;

  public XMLRPCRedirectException(String paramString)
  {
    super("HTTP Redirect");
    this.m_sRedirectUrl = paramString;
  }

  public String getRedirectUrl()
  {
    return this.m_sRedirectUrl;
  }
}

/* Location:           D:\tools\new-version-dex2jar\dex2jar-0.0.9.7\classes_dex2jar.jar
 * Qualified Name:     org.xmlrpc.android.XMLRPCRedirectException
 * JD-Core Version:    0.6.0
 */