/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: D:\\hzrobotworkspace\\FaceRecognition\\xiaoEsdk\\app\\src\\main\\aidl\\com\\cityeasy\\aidl\\ISpeechAsr.aidl
 */
package com.cityeasy.aidl;
public interface ISpeechAsr extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.cityeasy.aidl.ISpeechAsr
{
private static final java.lang.String DESCRIPTOR = "com.cityeasy.aidl.ISpeechAsr";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.cityeasy.aidl.ISpeechAsr interface,
 * generating a proxy if needed.
 */
public static com.cityeasy.aidl.ISpeechAsr asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.cityeasy.aidl.ISpeechAsr))) {
return ((com.cityeasy.aidl.ISpeechAsr)iin);
}
return new com.cityeasy.aidl.ISpeechAsr.Stub.Proxy(obj);
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
case TRANSACTION_cancel:
{
data.enforceInterface(DESCRIPTOR);
this.cancel();
reply.writeNoException();
return true;
}
case TRANSACTION_isListening:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.isListening();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
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
case TRANSACTION_getParameter:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _result = this.getParameter(_arg0);
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_startListening:
{
data.enforceInterface(DESCRIPTOR);
com.cityeasy.aidl.ISpeechAsrListener _arg0;
_arg0 = com.cityeasy.aidl.ISpeechAsrListener.Stub.asInterface(data.readStrongBinder());
int _result = this.startListening(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.cityeasy.aidl.ISpeechAsr
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
@Override public void cancel() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_cancel, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public boolean isListening() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isListening, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
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
@Override public java.lang.String getParameter(java.lang.String var1) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(var1);
mRemote.transact(Stub.TRANSACTION_getParameter, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int startListening(com.cityeasy.aidl.ISpeechAsrListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_startListening, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_cancel = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_isListening = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_setParameter = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_getParameter = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_startListening = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
}
public void cancel() throws android.os.RemoteException;
public boolean isListening() throws android.os.RemoteException;
public boolean setParameter(java.lang.String key, java.lang.String value) throws android.os.RemoteException;
public java.lang.String getParameter(java.lang.String var1) throws android.os.RemoteException;
public int startListening(com.cityeasy.aidl.ISpeechAsrListener listener) throws android.os.RemoteException;
}
