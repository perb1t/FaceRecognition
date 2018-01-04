/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: D:\\hzrobotworkspace\\FaceRecognition\\xiaoEsdk\\app\\src\\main\\aidl\\com\\cityeasy\\aidl\\IInfraredListener.aidl
 */
package com.cityeasy.aidl;
public interface IInfraredListener extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.cityeasy.aidl.IInfraredListener
{
private static final java.lang.String DESCRIPTOR = "com.cityeasy.aidl.IInfraredListener";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.cityeasy.aidl.IInfraredListener interface,
 * generating a proxy if needed.
 */
public static com.cityeasy.aidl.IInfraredListener asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.cityeasy.aidl.IInfraredListener))) {
return ((com.cityeasy.aidl.IInfraredListener)iin);
}
return new com.cityeasy.aidl.IInfraredListener.Stub.Proxy(obj);
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
case TRANSACTION_onInfraredChanged:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
this.onInfraredChanged(_arg0);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.cityeasy.aidl.IInfraredListener
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
@Override public void onInfraredChanged(boolean status) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((status)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_onInfraredChanged, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_onInfraredChanged = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
public void onInfraredChanged(boolean status) throws android.os.RemoteException;
}
