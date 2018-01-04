/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: D:\\hzrobotworkspace\\FaceRecognition\\xiaoEsdk\\app\\src\\main\\aidl\\com\\cityeasy\\aidl\\ISpeechTts.aidl
 */
package com.cityeasy.aidl;
public interface ISpeechTts extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.cityeasy.aidl.ISpeechTts
{
private static final java.lang.String DESCRIPTOR = "com.cityeasy.aidl.ISpeechTts";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.cityeasy.aidl.ISpeechTts interface,
 * generating a proxy if needed.
 */
public static com.cityeasy.aidl.ISpeechTts asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.cityeasy.aidl.ISpeechTts))) {
return ((com.cityeasy.aidl.ISpeechTts)iin);
}
return new com.cityeasy.aidl.ISpeechTts.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_isSpeaking:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.isSpeaking();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_pauseSpeaking:
{
data.enforceInterface(DESCRIPTOR);
this.pauseSpeaking();
reply.writeNoException();
return true;
}
case TRANSACTION_resumeSpeaking:
{
data.enforceInterface(DESCRIPTOR);
this.resumeSpeaking();
reply.writeNoException();
return true;
}
case TRANSACTION_setParameter:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
boolean _result = this.setParameter(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_startSpeaking:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
com.cityeasy.aidl.ISpeechTtsListener _arg1;
_arg1 = com.cityeasy.aidl.ISpeechTtsListener.Stub.asInterface(data.readStrongBinder());
int _result = this.startSpeaking(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_stopSpeaking:
{
data.enforceInterface(DESCRIPTOR);
this.stopSpeaking();
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.cityeasy.aidl.ISpeechTts
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public boolean isSpeaking() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isSpeaking, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void pauseSpeaking() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_pauseSpeaking, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void resumeSpeaking() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_resumeSpeaking, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public boolean setParameter(java.lang.String key, java.lang.String value) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(key);
_data.writeString(value);
mRemote.transact(Stub.TRANSACTION_setParameter, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int startSpeaking(java.lang.String text, com.cityeasy.aidl.ISpeechTtsListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(text);
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_startSpeaking, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void stopSpeaking() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_stopSpeaking, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_isSpeaking = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_pauseSpeaking = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_resumeSpeaking = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_setParameter = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_startSpeaking = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_stopSpeaking = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
}
public boolean isSpeaking() throws android.os.RemoteException;
public void pauseSpeaking() throws android.os.RemoteException;
public void resumeSpeaking() throws android.os.RemoteException;
public boolean setParameter(java.lang.String key, java.lang.String value) throws android.os.RemoteException;
public int startSpeaking(java.lang.String text, com.cityeasy.aidl.ISpeechTtsListener listener) throws android.os.RemoteException;
public void stopSpeaking() throws android.os.RemoteException;
}
