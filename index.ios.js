/**
 * @providesModule JitsiMeet
 */

import { NativeModules, requireNativeComponent } from 'react-native';

export const JitsiMeetView = requireNativeComponent('RNJitsiMeetView');
export const JitsiMeetModule = NativeModules.RNJitsiMeetView;
const call = JitsiMeetModule.call;
const audioCall = JitsiMeetModule.audioCall;
JitsiMeetModule.call = (confSettings, userInfo) => {
  confSettings = confSettings || {};
  userInfo = userInfo || {};
  call(confSettings, userInfo);
}
JitsiMeetModule.audioCall = (url, userInfo) => {
  userInfo = userInfo || {};
  audioCall(url, userInfo);
}
export default JitsiMeetModule;