/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.jasig.ssp.model.security.oauth;


import org.hibernate.annotations.Immutable;
import org.hibernate.validator.constraints.NotEmpty;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;


@Entity
@Immutable
@Table(name = "oauth_nonce")
@IdClass(value=OAuth1Nonce.NoncePrimaryKey.class)
public class OAuth1Nonce {

    @Id
    @Column(name = "consumer_key", nullable = false, length = 50)
    @NotNull
    @NotEmpty
    @Size(max = 50)
    private String consumerKey;

    @Id
    @Column(name = "nonce", nullable = false, length = 64)
    @NotNull
    @NotEmpty
    @Size(max = 64)
    private String nonce;

    @Id
    @Column(name = "nonce_timestamp", nullable = false)
    @NotNull
    private Long nonceTimestamp;

    public OAuth1Nonce() {}

    public OAuth1Nonce(String consumerKey, Long nonceTimestamp, String nonce) {
        this.consumerKey = consumerKey;
        this.nonceTimestamp = nonceTimestamp;
        this.nonce = nonce;
    }

    public String getConsumerKey () {
        return consumerKey;
    }

    public void setConsumerKey (final String consumerKey) {
        this.consumerKey = consumerKey;
    }

    public String getNonce () {
        return nonce;
    }

    public void setNonce (final String nonce) {
        this.nonce = nonce;
    }

    public Long getNonceTimestamp () {
        return nonceTimestamp;
    }

    public void setNonceTimestamp (final Long nonceTimestamp) {
        this.nonceTimestamp = nonceTimestamp;
    }

    protected static class NoncePrimaryKey implements Serializable {
        protected String consumerKey;
        protected Long nonceTimestamp;
        protected String nonce;

        public NoncePrimaryKey() {}

        public NoncePrimaryKey(final String consumerKey, Long nonceTimestamp, final String nonce) {
            this.consumerKey = consumerKey;
            this.nonceTimestamp = nonceTimestamp;
            this.nonce = nonce;
        }

        public String getNonce() {
            return nonce;
        }

        public Long getNonceTimestamp() {
            return nonceTimestamp;
        }

        public String getConsumerKey() {
            return consumerKey;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof NoncePrimaryKey)) return false;

            NoncePrimaryKey that = (NoncePrimaryKey) o;

            if (consumerKey != null ? !consumerKey.equals(that.consumerKey) : that.consumerKey != null) return false;
            if (nonce != null ? !nonce.equals(that.nonce) : that.nonce != null) return false;
            if (nonceTimestamp != null ? !nonceTimestamp.equals(that.nonceTimestamp) : that.nonceTimestamp != null)
                return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = consumerKey != null ? consumerKey.hashCode() : 0;
            result = 31 * result + (nonceTimestamp != null ? nonceTimestamp.hashCode() : 0);
            result = 31 * result + (nonce != null ? nonce.hashCode() : 0);
            return result;
        }
    }
}
