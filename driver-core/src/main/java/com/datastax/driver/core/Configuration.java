/*
 *      Copyright (C) 2012 DataStax Inc.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package com.datastax.driver.core;

import com.datastax.driver.core.policies.*;

/**
 * The configuration of the cluster.
 * It configures the following:
 * <ul>
 *   <li>Cassandra binary protocol level configuration (compression).</li>
 *   <li>Connection pooling configurations.</li>
 *   <li>low-level TCP configuration options (tcpNoDelay, keepAlive, ...).</li>
 * </ul>
 */
public class Configuration {

    private final Policies policies;

    private final ProtocolOptions protocolOptions;
    private final PoolingOptions poolingOptions;
    private final SocketOptions socketOptions;

    private final AuthInfoProvider authProvider;
    private final boolean metricsEnabled;

    /*
     * Creates a configuration object.
     */
    public Configuration() {
        this(new Policies(),
             new ProtocolOptions(),
             new PoolingOptions(),
             new SocketOptions(),
             AuthInfoProvider.NONE,
             true);
    }

    /**
     * Creates a configuration with the specified parameters.
     * 
     * @param policies the policies to use
     * @param protocolOptions the protocol options to use
     * @param poolingOptions the pooling options to use
     * @param socketOptions the socket options to use
     * @param authProvider the authentication provider to use
     * @param metricsEnabled whether to enable metrics or not
     */
    public Configuration(Policies policies,
                         ProtocolOptions protocolOptions,
                         PoolingOptions poolingOptions,
                         SocketOptions socketOptions,
                         AuthInfoProvider authProvider,
                         boolean metricsEnabled) {
        this.policies = policies;
        this.protocolOptions = protocolOptions;
        this.poolingOptions = poolingOptions;
        this.socketOptions = socketOptions;
        this.authProvider = authProvider;
        this.metricsEnabled = metricsEnabled;
    }

    void register(Cluster.Manager manager) {
        protocolOptions.register(manager);
        poolingOptions.register(manager);
    }

    /**
     * Returns the policies set for the cluster.
     *
     * @return the policies set for the cluster.
     */
    public Policies getPolicies() {
        return policies;
    }

    /**
     * Returns the low-level TCP configuration options used (tcpNoDelay, keepAlive, ...).
     *
     * @return the socket options.
     */
    public SocketOptions getSocketOptions() {
        return socketOptions;
    }

    /**
     * Returns the Cassandra binary protocol level configuration (compression).
     *
     * @return the protocol options.
     */
    public ProtocolOptions getProtocolOptions() {
        return protocolOptions;
    }

    /**
     * Returns the connection pooling configuration.
     *
     * @return the pooling options.
     */
    public PoolingOptions getPoolingOptions() {
        return poolingOptions;
    }

    /**
     * Returns the authentication provider used to connect to the Cassandra cluster.
     *
     * @return the authentication provider in use.
     */
    public AuthInfoProvider getAuthInfoProvider() {
        return authProvider;
    }

    /**
     * Returns whether metrics collection is enabled for the cluster instance.
     * <p>
     * Metrics collection is enabled by default but can be disabled at cluster
     * construction time through {@link Cluster.Builder#withoutMetrics}.
     *
     * @return whether metrics collection is enabled for the cluster instance.
     */
    public boolean isMetricsEnabled() {
        return metricsEnabled;
    }
}
