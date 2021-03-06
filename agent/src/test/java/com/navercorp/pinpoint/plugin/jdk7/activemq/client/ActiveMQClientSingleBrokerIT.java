/*
 * Copyright 2016 Naver Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.navercorp.pinpoint.plugin.jdk7.activemq.client;

import com.navercorp.pinpoint.common.Version;
import com.navercorp.pinpoint.plugin.jdk7.activemq.client.util.ActiveMQClientITHelper;
import com.navercorp.pinpoint.plugin.jdk7.activemq.client.util.TestBroker;
import com.navercorp.pinpoint.test.plugin.Dependency;
import com.navercorp.pinpoint.test.plugin.PinpointAgent;
import com.navercorp.pinpoint.test.plugin.PinpointConfig;
import com.navercorp.pinpoint.test.plugin.PinpointPluginTestSuite;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import java.util.Arrays;

/**
 * @author HyunGil Jeong
 */
@RunWith(PinpointPluginTestSuite.class)
@PinpointAgent("agent/target/pinpoint-agent-" + Version.VERSION)
@PinpointConfig("activemq/client/pinpoint-activemq-client.config")
// 5.4.1 bug creates activemq-data directory even if persistence is set to false - skip it
// 5.5.x activemq-all missing slf4j binder - just skip instead of supplying one
@Dependency({"org.apache.activemq:activemq-all:[5.1.0,5.4.1),[5.4.2,5.4.max],[5.6.0,)"})
public class ActiveMQClientSingleBrokerIT extends ActiveMQClientITBase {

    private static final String BROKER_NAME = "Test_Broker";
    private static final String BROKER_URL = TestBroker.DEFAULT_BROKER_URL;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        ActiveMQClientITHelper.startBrokers(Arrays.asList(
                new TestBroker.TestBrokerBuilder(BROKER_NAME)
                        .addConnector(BROKER_URL)
                        .build()
        ));
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        ActiveMQClientITHelper.stopBrokers();
    }

    @Override
    protected String getProducerBrokerName() {
        return BROKER_NAME;
    }

    @Override
    protected String getProducerBrokerUrl() {
        return BROKER_URL;
    }

    @Override
    protected String getConsumerBrokerName() {
        return BROKER_NAME;
    }

    @Override
    protected String getConsumerBrokerUrl() {
        return BROKER_URL;
    }

}
