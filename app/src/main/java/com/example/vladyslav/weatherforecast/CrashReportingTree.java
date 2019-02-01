package com.example.vladyslav.weatherforecast;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

import timber.log.Timber;

public class CrashReportingTree extends Timber.DebugTree{

    public CrashReportingTree(){ }

    @Override
    protected @Nullable String createStackElementTag(@NotNull StackTraceElement element) {

        return String.format(Locale.getDefault(),
                "[TIMBER] %s.%s() [#%d]",
                super.createStackElementTag(element),
                element.getMethodName(),
                element.getLineNumber());
    }
}
