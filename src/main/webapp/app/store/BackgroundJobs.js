/*
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
Ext.define('Ssp.store.BackgroundJobs', {
    extend: 'Ext.data.Store',
    mixins: [ 'Deft.mixin.Injectable'],
    model: 'Ssp.model.BackgroundJob',
    autoLoad: false,
    constructor: function(){
        var me=this;
        me.callParent( arguments );
        Ext.apply(this, { proxy: '', autoLoad: false });
        me.load();

        return me;
    },
    load: function() {
        var me=this;
        var data = [
            { name:'sendmessages', displayName: 'send_messages',
                description: 'Sends queued messages (email).',
                defaultTime: 'Every 2.5 minutes', configName: 'N/A', running: '', executable: 'false'},
            { name:'synccoaches', displayName: 'sync_coaches',
                description: 'Updates the list of Coaches which appear in the various Caseload drop-downs.',
                defaultTime: 'Every 5 minutes', configName: 'N/A', running: '', executable: 'false'},
            { name:'processcaseloadbulkaddreassignment', displayName: 'process_caseload_bulk_add_reassignment',
                description: 'Processes and executes queued Bulk Actions from Caseload.',
                defaultTime: 'Every 5 minutes', configName: 'N/A', running: '', executable: 'false'},
            { name:'externalpersonsync', displayName: 'external_person_sync',
                description: 'Executes the external person sync task which syncs added students with any updated values in v_external_person.',
                defaultTime: 'Nightly at 1 a.m.', configName: 'task_external_person_sync_trigger', running: '', executable: 'true'},
            { name:'directorypersonrefresh1', displayName: 'directory_person_refresh',
                description: 'Executes the mv_directory_person refresh task which re-builds the view for Caseload, Watchlist and Person Search tabs. This runs automatically after the external person sync task.',
                defaultTime: 'Nightly after external_person_sync', configName: 'N/A', running: '', executable: 'true'},
            //{ name:'directorypesonrefreshblue', displayName: 'directory_person_refresh_blue',
            //    description: 'Executes the mv_directory_person_blue refresh task which re-builds the alternate view for Caseload,Watchlist and Person Search tabs. This is run nightly after the external person sync task.',
            //    defaultTime: 'Nightly after external_person_sync', configName: 'N/A', running: '', executable: 'true'}
            { name:'sendearlyalertreminders', displayName: 'send_early_alert_reminders',
                description: 'Executes the send Early Alert reminders task which notifies coaches or others set in the config "ear_reminder_recipients" ' +
                'if an Alert hasn\'t been responded to in configured a time-frame (default is 2 days, config: "maximum_days_before_early_alert_response").',
                defaultTime: 'Nightly at 4 a.m.', configName: 'task_scheduler_early_alert_trigger', running: '', executable: 'true'},
            { name:'sendtaskreminders', displayName: 'send_task_reminders',
                description: 'Executes the send Task reminders job which notifies coaches/students if an Action Plan Task is due or overdue. Notification before due is in: "numberOfDaysPriorForTaskReminders".',
                defaultTime: 'Nightly at 2 a.m.', configName: 'task_scheduler_early_alert_trigger', running: '', executable: 'true'},
            { name:'prunemessagequeue', displayName: 'prune_message_queue',
                description: 'Executes the prune Message queue task which archives old Messages in the "message" table after 30 days to the archive table.',
                defaultTime: 'Nightly at 10 p.m.', configName: 'task_message_queue_pruning_trigger', running: '', executable: 'true'},
            { name:'mapstatuscalculation', displayName: 'map_status_calculation',
                description: 'Executes the MAP status calculation task which calculates On/Off Plan Status for students with active plans.',
                defaultTime: 'Nightly at 3 a.m.', configName: 'task_scheduler_map_plan_status_calculation_trigger', running: '', executable: 'true'},
            { name:'ssgcoursewithdrawnotify', displayName: 'ssg_course_withdraw_notify',
                description: 'Executes the special service group course withdrawal notification to advisor task. ' +
                'This emails advisors if a student has withdrawn from a current course and is assigned to a configured SSG that has notify turned on.',
                defaultTime: 'Nightly at 5 a.m.', configName: 'task_scheduler_map_plan_status_calculation_trigger', running: '', executable: 'true'},
            { name:'culloauth1nonces', displayName: 'cull_oauth1_nonces',
                description: 'Culls expired Oauth1 Nonces',
                defaultTime: 'Every 2 hours', configName: 'task_scheduler_oauth_nonce_cull_trigger', running: '', executable: 'false'}
        ];

        me.loadData( data );
        return me;
    }
});
