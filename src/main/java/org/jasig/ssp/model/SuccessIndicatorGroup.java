/**
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.model;

import com.google.common.base.Predicate;
import org.jasig.ssp.transferobject.EvaluatedSuccessIndicatorTO;

import javax.annotation.Nullable;

public enum SuccessIndicatorGroup {
    STUDENT {
        private Predicate<EvaluatedSuccessIndicatorTO> DTO_PREDICATE = newTransferObjectPredicate(this);
        @Override
        public Predicate<EvaluatedSuccessIndicatorTO> transferObjectPredicate() {
            return DTO_PREDICATE;
        }
    },INTERVENTION {
        private Predicate<EvaluatedSuccessIndicatorTO> DTO_PREDICATE = newTransferObjectPredicate(this);
        @Override
        public Predicate<EvaluatedSuccessIndicatorTO> transferObjectPredicate() {
            return DTO_PREDICATE;
        }
    },RISK {
        private Predicate<EvaluatedSuccessIndicatorTO> DTO_PREDICATE = newTransferObjectPredicate(this);
        @Override
        public Predicate<EvaluatedSuccessIndicatorTO> transferObjectPredicate() {
            return DTO_PREDICATE;
        }
    };
    private static Predicate<EvaluatedSuccessIndicatorTO> newTransferObjectPredicate(final SuccessIndicatorGroup forValue) {
        return new Predicate<EvaluatedSuccessIndicatorTO>() {
            @Override
            public boolean apply(@Nullable EvaluatedSuccessIndicatorTO input) {
                return input == null ? forValue == null : input.getIndicatorGroupCode() == forValue;
            }
        };
    }
    public abstract Predicate<EvaluatedSuccessIndicatorTO> transferObjectPredicate();
}
