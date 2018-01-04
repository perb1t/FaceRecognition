/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: D:\\hzrobotworkspace\\FaceRecognition\\xiaoEsdk\\app\\src\\main\\aidl\\com\\cityeasy\\aidl\\ISpeechAsrListener.aidl
 */
package com.cityeasy.aidl;
public interface ISpeechAsrListener extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.cityeasy.aidl.ISpeechAsrListener
{
private static final java.lang.String DESCRIPTOR = "com.cityeasy.aidl.ISpeechAsrListener";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.cityeasy.aidl.ISpeechAsrListener interface,
 * generating a proxy if needed.
 */
public static com.cityeasy.aidl.ISpeechAsrListener asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.cityeasy.aidl.ISpeechAsrListener))) {
return ((com.cityeasy.aidl.ISpeechAsrListener)iin);
}
return new com.cityeasy.aidl.ISpeechAsrListener.Stub.Proxy(obj);
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
case TRANSACTION_onVolumeChanged:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.onVolumeChanged(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_onBeginOfSpeech:
{
data.enforceInterface(DESCRIPTOR);
this.onBeginOfSpeech();
reply.writeNoException();
return true;
}
case TRANSACTION_onEndOfSpeech:
{
data.enforceInterface(DESCRIPTOR);
this.onEndOfSpeech();
reply.writeNoException();
return true;
}
case TRANSACTION_onResult:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _result = this.onResult(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_onError:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.onError(_arg0);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.cityeasy.aidl.ISpeechAsrListener
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
@Override public void onVolumeChanged(int var1) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(var1);
mRemote.transact(Stub.TRANSACTION_onVolumeChanged, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onBeginOfSpeech() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_onBeginOfSpeech, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onEndOfSpeech() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_onEndOfSpeech, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public boolean onResult(java.lang.String result) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(result);
mRemote.transact(Stub.TRANSACTION_onResult, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void onError(int var1) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(var1);
mRemote.transact(Stub.TRANSACTION_onError, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_onVolumeChanged = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_onBeginOfSpeech = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_onEndOfSpeech = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_onResult = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_onError = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
}
public void onVolumeChanged(int var1) throws android.os.RemoteException;
public void onBeginOfSpeech() throws android.os.RemoteException;
public void onEndOfSpeech() throws android.os.RemoteException;
public boolean onResult(java.lang.String result) throws android.os.RemoteException;
public void onError(int var1) throws android.os.RemoteException;
}
