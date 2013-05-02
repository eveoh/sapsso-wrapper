/**
 * (C) Copyright 2000-2005 SAP AG Walldorf
 * 
 * Author: SAP AG, Security Development
 * 
 * SAP AG DISCLAIMS ALL WARRANTIES WITH REGARD TO THIS SOFTWARE,
 * INCLUDING ALL IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS, IN NO
 * EVENT SHALL SAP AG BE LIABLE FOR ANY SPECIAL, INDIRECT OR CONSEQUENTIAL
 * DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR
 * PROFITS, WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS
 * ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR PERFORMANCE
 * OF THIS SOFTWARE.
 * 
 * This class provides wrapper functionality for SSO2Ticket
 * (SAP Logon Ticket) in Java.
 * 
 * @version 1.5 2005
 * 
 */

package com.mysap.sso;


public class SSO2Ticket {

    public static final int ISSUER_CERT_SUBJECT = 0;
    public static final int ISSUER_CERT_ISSUER = 1;
    public static final int ISSUER_CERT_SERIALNO = 2;

    private static boolean initialized = false;
    public static String SECLIBRARY;
    public static String SSO2TICKETLIBRARY = "sapssoext";

    static {
        if (System.getProperty("os.name").startsWith("Win")) {
            SECLIBRARY = "sapsecu.dll";
        }
        else {
            SECLIBRARY = "libsapsecu.so";
        }
        try {
            System.loadLibrary(SSO2TICKETLIBRARY);
            System.out.println("SAPSSOEXT loaded.");
            if (!init(SECLIBRARY)) {
                throw new RuntimeException("Could not initialize library: " + SECLIBRARY);
            }
            System.out.println("SAPSSOEXT initialized.");
        }
        catch (Throwable e) {
            System.err.println("Error during initialization of SSO2TICKET: " + e.toString());
        }
        System.out.println("static part ends");
    }

    /**
     * Initialization
     * 
     * @param seclib
     *            location of ssf-implemenation
     * 
     * @return true/false whether initailisation was ok
     */
    private static native synchronized boolean init(String seclib);

    /**
     * Returns internal version.
     * 
     * @return version
     */
    public static native synchronized String getVersion();

    /**
     * eval ticket
     * 
     * @param ticket
     *            the ticket
     * @param pab
     *            location of pab
     * @param pab_password
     *            password for access the pab
     * 
     * @return Object array with:
     *         [0] = (String)user, [1] = (String)sysid, [2] = (String)client , [3] = (byte[])certificate
     *         [4] = (String)portalUser, [5] = (String)authSchema, [6] = validity
     * 
     */
    public static native synchronized Object[] evalLogonTicket(String ticket, String pab, String pab_password)
            throws Exception;
}
