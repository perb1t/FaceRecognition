/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: D:\\hzrobotworkspace\\FaceRecognition\\xiaoEsdk\\app\\src\\main\\aidl\\com\\cityeasy\\aidl\\IVoiceAssistService.aidl
 */
package com.cityeasy.aidl;
public interface IVoiceAssistService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.cityeasy.aidl.IVoiceAssistService
{
private static final java.lang.String DESCRIPTOR = "com.cityeasy.aidl.IVoiceAssistService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.cityeasy.aidl.IVoiceAssistService interface,
 * generating a proxy if needed.
 */
public static com.cityeasy.aidl.IVoiceAssistService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.cityeasy.aidl.IVoiceAssistService))) {
return ((com.cityeasy.aidl.IVoiceAssistService)iin);
}
return new com.cityeasy.aidl.IVoiceAssistService.Stub.Proxy(obj);
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
case TRANSACTION_startSpeakAction:
{
data.enforceInterface(DESCRIPTOR);
this.startSpeakAction();
reply.writeNoException();
return true;
}
case TRANSACTION_startAction:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.startAction(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_registerTouchPanelListener:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
com.cityeasy.aidl.ITouchPanelListener _arg1;
_arg1 = com.cityeasy.aidl.ITouchPanelListener.Stub.asInterface(data.readStrongBinder());
this.registerTouchPanelListener(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_unregisterTouchPanelListener:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
com.cityeasy.aidl.ITouchPanelListener _arg1;
_arg1 = com.cityeasy.aidl.ITouchPanelListener.Stub.asInterface(data.readStrongBinder());
this.unregisterTouchPanelListener(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_registerInfraredListener:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
com.cityeasy.aidl.IInfraredListener _arg1;
_arg1 = com.cityeasy.aidl.IInfraredListener.Stub.asInterface(data.readStrongBinder());
this.registerInfraredListener(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_unregisterInraredListener:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
com.cityeasy.aidl.IInfraredListener _arg1;
_arg1 = com.cityeasy.aidl.IInfraredListener.Stub.asInterface(data.readStrongBinder());
this.unregisterInraredListener(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_isVoiceActivityRunning:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.isVoiceActivityRunning();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
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
case TRANSACTION_registerAsrResult:
{
data.enforceInterface(DESCRIPTOR);
com.cityeasy.aidl.ISpeechAsrListener _arg0;
_arg0 = com.cityeasy.aidl.ISpeechAsrListener.Stub.asInterface(data.readStrongBinder());
this.registerAsrResult(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_unregisterAsrResult:
{
data.enforceInterface(DESCRIPTOR);
com.cityeasy.aidl.ISpeechAsrListener _arg0;
_arg0 = com.cityeasy.aidl.ISpeechAsrListener.Stub.asInterface(data.readStrongBinder());
this.unregisterAsrResult(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_stopListening:
{
data.enforceInterface(DESCRIPTOR);
this.stopListening();
reply.writeNoException();
return true;
}
case TRANSACTION_updateUserWord:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
com.cityeasy.aidl.ILexiconListener _arg1;
_arg1 = com.cityeasy.aidl.ILexiconListener.Stub.asInterface(data.readStrongBinder());
this.updateUserWord(_arg0, _arg1);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.cityeasy.aidl.IVoiceAssistService
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
/**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
@Override public void startSpeakAction() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_startSpeakAction, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void startAction(java.lang.String actionname) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(actionname);
mRemote.transact(Stub.TRANSACTION_startAction, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void registerTouchPanelListener(java.lang.String packageName, com.cityeasy.aidl.ITouchPanelListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(packageName);
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_registerTouchPanelListener, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void unregisterTouchPanelListener(java.lang.String packageName, com.cityeasy.aidl.ITouchPanelListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(packageName);
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_unregisterTouchPanelListener, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void registerInfraredListener(java.lang.String packageName, com.cityeasy.aidl.IInfraredListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(packageName);
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_registerInfraredListener, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void unregisterInraredListener(java.lang.String packageName, com.cityeasy.aidl.IInfraredListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(packageName);
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_unregisterInraredListener, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public boolean isVoiceActivityRunning() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isVoiceActivityRunning, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
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
@Override public void registerAsrResult(com.cityeasy.aidl.ISpeechAsrListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_registerAsrResult, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void unregisterAsrResult(com.cityeasy.aidl.ISpeechAsrListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_unregisterAsrResult, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void stopListening() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_stopListening, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void updateUserWord(java.lang.String userwork, com.cityeasy.aidl.ILexiconListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(userwork);
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_updateUserWord, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_startSpeakAction = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_startAction = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_registerTouchPanelListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_unregisterTouchPanelListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_registerInfraredListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_unregisterInraredListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_isVoiceActivityRunning = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_startListening = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_registerAsrResult = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_unregisterAsrResult = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_stopListening = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTION_updateUserWord = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
}
/**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
public void startSpeakAction() throws android.os.RemoteException;
public void startAction(java.lang.String actionname) throws android.os.RemoteException;
public void registerTouchPanelListener(java.lang.String packageName, com.cityeasy.aidl.ITouchPanelListener listener) throws android.os.RemoteException;
public void unregisterTouchPanelListener(java.lang.String packageName, com.cityeasy.aidl.ITouchPanelListener listener) throws android.os.RemoteException;
public void registerInfraredListener(java.lang.String packageName, com.cityeasy.aidl.IInfraredListener listener) throws android.os.RemoteException;
public void unregisterInraredListener(java.lang.String packageName, com.cityeasy.aidl.IInfraredListener listener) throws android.os.RemoteException;
public boolean isVoiceActivityRunning() throws android.os.RemoteException;
public int startListening(com.cityeasy.aidl.ISpeechAsrListener listener) throws android.os.RemoteException;
public void registerAsrResult(com.cityeasy.aidl.ISpeechAsrListener listener) throws android.os.RemoteException;
public void unregisterAsrResult(com.cityeasy.aidl.ISpeechAsrListener listener) throws android.os.RemoteException;
public void stopListening() throws android.os.RemoteException;
public void updateUserWord(java.lang.String userwork, com.cityeasy.aidl.ILexiconListener listener) throws android.os.RemoteException;
}
