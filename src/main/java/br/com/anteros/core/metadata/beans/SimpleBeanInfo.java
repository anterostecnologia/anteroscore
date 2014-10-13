/*******************************************************************************
 * Copyright 2012 Anteros Tecnologia
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package br.com.anteros.core.metadata.beans;


/** This is a support class to make it easier for people to provide
* BeanInfo classes.
* <p>
* It defaults to providing "noop" information, and can be selectively
* overriden to provide more explicit information on chosen topics.
* When the introspector sees the "noop" values, it will apply low
* level introspection and design patterns to automatically analyze
* the target bean.
*/

public class SimpleBeanInfo implements BeanInfo  {

   /**
    * Deny knowledge about the class and customizer of the bean.
    * You can override this if you wish to provide explicit info.
    */
   public BeanDescriptor  getBeanDescriptor() {
   return null;
   }

   /**
    * Deny knowledge of properties. You can override this
    * if you wish to provide explicit property info.
    */
   public PropertyDescriptor [] getPropertyDescriptors() {
   return null;
   }

   /**
    * Deny knowledge of a default property. You can override this
    * if you wish to define a default property for the bean.
    */
   public int getDefaultPropertyIndex() {
   return -1;
   }

   /**
    * Deny knowledge of event sets. You can override this
    * if you wish to provide explicit event set info.
    */
   public EventSetDescriptor [] getEventSetDescriptors() {
   return null;
   }

   /**
    * Deny knowledge of a default event. You can override this
    * if you wish to define a default event for the bean.
    */
   public int getDefaultEventIndex() {
   return -1;
   }

   /**
    * Deny knowledge of methods. You can override this
    * if you wish to provide explicit method info.
    */
   public MethodDescriptor [] getMethodDescriptors() {
   return null;
   }

   /**
    * Claim there are no other relevant BeanInfo objects. You
    * may override this if you want to (for example) return a
    * BeanInfo for a base class.
    */
   public BeanInfo [] getAdditionalBeanInfo() {
   return null;
   }


}
