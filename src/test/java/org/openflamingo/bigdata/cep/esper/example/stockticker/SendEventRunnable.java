/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package org.openflamingo.bigdata.cep.esper.example.stockticker;

import com.espertech.esper.client.EPServiceProvider;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SendEventRunnable implements Runnable {
    private Object eventToSend;
    private EPServiceProvider epService;

    public SendEventRunnable(EPServiceProvider epService, Object eventToSend) {
        this.epService = epService;
        this.eventToSend = eventToSend;
    }

    public void run() {
        try {
            epService.getEPRuntime().sendEvent(eventToSend);
        } catch (Exception ex) {
            log.fatal(ex);
        }
    }

    private static final Log log = LogFactory.getLog(SendEventRunnable.class);
}
