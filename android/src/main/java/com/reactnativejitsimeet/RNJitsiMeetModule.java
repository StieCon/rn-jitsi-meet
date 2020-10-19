package com.reactnativejitsimeet;

import android.util.Log;
import java.net.URL;
import java.net.MalformedURLException;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.bridge.ReadableMap;

@ReactModule(name = RNJitsiMeetModule.MODULE_NAME)
public class RNJitsiMeetModule extends ReactContextBaseJavaModule {
    public static final String MODULE_NAME = "RNJitsiMeetModule";
    private IRNJitsiMeetViewReference mJitsiMeetViewReference;

    public RNJitsiMeetModule(ReactApplicationContext reactContext, IRNJitsiMeetViewReference jitsiMeetViewReference) {
        super(reactContext);
        mJitsiMeetViewReference = jitsiMeetViewReference;
    }

    @Override
    public String getName() {
        return MODULE_NAME;
    }

    @ReactMethod
    public void initialize() {
        Log.d("JitsiMeet", "Initialize is deprecated in v2");
    }

    @ReactMethod
    public void call(ReadableMap call, ReadableMap userInfo) {
        UiThreadUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mJitsiMeetViewReference.getJitsiMeetView() != null) {
                    RNJitsiMeetUserInfo _userInfo = new RNJitsiMeetUserInfo();
                    if (userInfo != null) {
                        if (userInfo.hasKey("displayName")) {
                            _userInfo.setDisplayName(userInfo.getString("displayName"));
                          }
                          if (userInfo.hasKey("email")) {
                            _userInfo.setEmail(userInfo.getString("email"));
                          }
                          if (userInfo.hasKey("avatar")) {
                            String avatarURL = userInfo.getString("avatar");
                            try {
                                _userInfo.setAvatar(new URL(avatarURL));
                            } catch (MalformedURLException e) {
                            }
                          }
                    }

                    RNJitsiMeetConferenceOptions.Builder builder  = new RNJitsiMeetConferenceOptions.Builder()
                        .setAudioOnly(false)
                        .setUserInfo(_userInfo);

                    if (call.hasKey("serverURL")) {
                        String serverURL = call.getString("serverURL");
                        try {
                            builder.setServerURL(new URL(serverURL));
                        } catch (MalformedURLException e) {}
                    }
                    if (call.hasKey("room")) {
                        builder.setRoom(call.getString("room"));
                    }
                    if (call.hasKey("subject")) {
                        builder.setSubject(call.getString("subject"));
                    }
                    if (call.hasKey("jwt")) {
                        builder.setToken(call.getString("jwt"));
                    }
                    if (call.hasKey("startWithVideoMuted")){
                        builder.setVideoMuted(new String("YES").equals(call.getString("startWithVideoMuted")));  
                    }
                    if (call.hasKey("startWithAudioMuted")){
                        builder.setAudioMuted(new String("YES").equals(call.getString("startWithAudioMuted")));  
                    }
                    if (call.hasKey("pip")){
                        builder.setFeatureFlag("pip.enabled", new String("YES").equals(call.getString("pip")));  
                    }
                    if (call.hasKey("addPeople")){
                        builder.setFeatureFlag("add-people.enabled", new String("YES").equals(call.getString("addPeople")));  
                    }
                    if (call.hasKey("calendar")){
                        builder.setFeatureFlag("calendar.enabled", new String("YES").equals(call.getString("calendar")));  
                    }
                    if (call.hasKey("callIntegration")){
                        builder.setFeatureFlag("call-integration.enabled", new String("YES").equals(call.getString("callIntegration")));  
                    }
                    if (call.hasKey("closeCaptions")){
                        builder.setFeatureFlag("close-captions.enabled", new String("YES").equals(call.getString("closeCaptions")));  
                    }
                    if (call.hasKey("chat")){
                        builder.setFeatureFlag("chat.enabled", new String("YES").equals(call.getString("chat")));  
                    }
                    if (call.hasKey("invite")){
                        builder.setFeatureFlag("invite.enabled", new String("YES").equals(call.getString("invite")));  
                    }
                    if (call.hasKey("raiseHand")){
                        builder.setFeatureFlag("raise-hand.enabled", new String("YES").equals(call.getString("raiseHand")));  
                    }
                    if (call.hasKey("resolution")){
                        builder.setFeatureFlag("resolution", call.getString("resolution"));  
                    }

                    mJitsiMeetViewReference.getJitsiMeetView().join(builder.build());
                }
            }
        });
    }

    @ReactMethod
    public void audioCall(String url, ReadableMap userInfo) {
        UiThreadUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mJitsiMeetViewReference.getJitsiMeetView() != null) {
                    RNJitsiMeetUserInfo _userInfo = new RNJitsiMeetUserInfo();
                    if (userInfo != null) {
                        if (userInfo.hasKey("displayName")) {
                            _userInfo.setDisplayName(userInfo.getString("displayName"));
                          }
                          if (userInfo.hasKey("email")) {
                            _userInfo.setEmail(userInfo.getString("email"));
                          }
                          if (userInfo.hasKey("avatar")) {
                            String avatarURL = userInfo.getString("avatar");
                            try {
                                _userInfo.setAvatar(new URL(avatarURL));
                            } catch (MalformedURLException e) {
                            }
                          }
                    }
                    RNJitsiMeetConferenceOptions options = new RNJitsiMeetConferenceOptions.Builder()
                            .setRoom(url)
                            .setAudioOnly(true)
                            .setUserInfo(_userInfo)
                            .build();
                    mJitsiMeetViewReference.getJitsiMeetView().join(options);
                }
            }
        });
    }

    @ReactMethod
    public void endCall() {
        UiThreadUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mJitsiMeetViewReference.getJitsiMeetView() != null) {
                    mJitsiMeetViewReference.getJitsiMeetView().leave();
                }
            }
        });
    }
}
