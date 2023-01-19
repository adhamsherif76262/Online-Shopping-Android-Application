package com.example.online_shopping;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class SimpleFragmentPagerAdapter  extends FragmentPagerAdapter
{
    public SimpleFragmentPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Voice";
            case 1:
                return "Voice";
            case 2:
                return "Voice";
        }

        return super.getPageTitle(position);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new VoiceSearchFragment();
            case 1:
                return new VoiceSearchFragment();
            case 2:
                return new VoiceSearchFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
