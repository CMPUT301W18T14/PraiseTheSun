/*
 * Copyright © 2018 Chase Buhler, Imtihan Ahmed, Thomas Lafrance, Ryan Romano, Stephen Packer,
 * Alden Emerson Ern Tan
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ca.ualbert.cs.tasko.NotificationArtifacts;

/**
 * A simple enumeration for the different type of notifications. Depending on the status, the
 * message of the notification, and who it was sent to will change.
 * @see Notification
 *
 * @author spack
 */
public enum NotificationType {
    TASK_REQUESTER_RECEIVED_BID_ON_TASK, TASK_PROVIDER_BID_ACCEPTED, TASK_PROVIDER_BID_DECLINED,
    RATING, TASK_DELETED, TASK_REQUESTER_REPOSTED_TASK, INCOMPLETE_TASK_RATING
}

