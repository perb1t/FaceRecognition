/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: D:\\hzrobotworkspace\\FaceRecognition\\xiaoEsdk\\app\\src\\main\\aidl\\com\\cityeasy\\aidl\\ISpeechTtsListener.aidl
 */
package com.cityeasy.aidl;
public interface ISpeechTtsListener extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.cityeasy.aidl.ISpeechTtsListener
{
private static final java.lang.String DESCRIPTOR = "com.cityeasy.aidl.ISpeechTtsListener";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.cityeasy.aidl.ISpeechTtsListener interface,
 * generating a proxy if needed.
 */
public static com.cityeasy.aidl.ISpeechTtsListener asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.cityeasy.aidl.ISpeechTtsListener))) {
return ((com.cityeasy.aidl.ISpeechTtsListener)iin);
}
return new com.cityeasy.aidl.ISpeechTtsListener.Stub.Proxy(obj);
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
case TRANSACTION_onSpeakBegin:
{
data.enforceInterface(DESCRIPTOR);
this.onSpeakBegin();
reply.writeNoException();
return true;
}
case TRANSACTION_onSpeakPaused:
{
data.enforceInterface(DESCRIPTOR);
this.onSpeakPaused();
reply.writeNoException();
return true;
}
case TRANSACTION_onSpeakProgress:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
this.onSpeakProgress(_arg0, _arg1, _arg2);
reply.writeNoException();
return true;
}
case TRANSACTION_onSpeakResumed:
{
data.enforceInterface(DESCRIPTOR);
this.onSpeakResumed();
reply.writeNoException();
return true;
}
case TRANSACTION_onCompleted:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.onCompleted(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_onBufferProgress:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
java.lang.String _arg3;
_arg3 = data.readString();
this.onBufferProgress(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.cityeasy.aidl.ISpeechTtsListener
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
@Override public void onSpeakBegin() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_onSpeakBegin, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onSpeakPaused() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_onSpeakPaused, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onSpeakProgress(int progress, int beginPos, int endPos) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(progress);
_data.writeInt(beginPos);
_data.writeInt(endPos);
mRemote.transact(Stub.TRANSACTION_onSpeakProgress, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onSpeakResumed() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_onSpeakResumed, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onCompleted(int error) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(error);
mRemote.transact(Stub.TRANSACTION_onCompleted, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onBufferProgress(int progress, int beginPos, int endPos, java.lang.String info) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(progress);
_data.writeInt(beginPos);
_data.writeInt(endPos);
_data.writeString(info);
mRemote.transact(Stub.TRANSACTION_onBufferProgress, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_onSpeakBegin = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_onSpeakPaused = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_onSpeakProgress = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_onSpeakResumed = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_onCompleted = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_onBufferProgress = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
}
public void onSpeakBegin() throws android.os.RemoteException;
public void onSpeakPaused() throws android.os.RemoteException;
public void onSpeakProgress(int progress, int beginPos, int endPos) throws android.os.RemoteException;
public void onSpeakResumed() throws android.os.RemoteException;
public void onCompleted(int error) throws android.os.RemoteException;
public void onBufferProgress(int progress, int beginPos, int endPos, java.lang.String info) throws android.os.RemoteException;
}
