/*
 * Copyright (c) 1999-2004 Sourceforge JACOB Project.
 * All rights reserved. Originator: Dan Adler (http://danadler.com).
 * Get more information about JACOB at http://sourceforge.net/projects/jacob-project
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
#include <jni.h>
/* Header for class com_jacob_com_DispatchProxy */

#ifndef _Included_com_sap_smb_sbo_wrapper_com_DispatchProxy
#define _Included_com_sap_smb_sbo_wrapper_com_DispatchProxy
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_jacob_com_DispatchProxy
 * Method:    MarshalIntoStream
 * Signature: (Lcom/sap/smb/sbo/wrapper/com/Dispatch;)V
 */
JNIEXPORT void JNICALL Java_com_sap_smb_sbo_wrapper_com_DispatchProxy_MarshalIntoStream
  (JNIEnv *, jobject, jobject);

/*
 * Class:     com_jacob_com_DispatchProxy
 * Method:    MarshalFromStream
 * Signature: ()Lcom/sap/smb/sbo/wrapper/com/Dispatch;
 */
JNIEXPORT jobject JNICALL Java_com_sap_smb_sbo_wrapper_com_DispatchProxy_MarshalFromStream
  (JNIEnv *, jobject);

/*
 * Class:     com_jacob_com_DispatchProxy
 * Method:    release
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_sap_smb_sbo_wrapper_com_DispatchProxy_release
  (JNIEnv *, jobject);

#ifdef __cplusplus
}
#endif
#endif
