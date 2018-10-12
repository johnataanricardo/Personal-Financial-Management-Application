package info.seufinanceiro.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import info.seufinanceiro.R;

public class HomeFragment extends Fragment {

    private ContentTab listener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_layout, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            Integer month = bundle.getInt("tab");
            listener.setContentTab(month);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ContentTab) {
            listener = (ContentTab) context;
        } else {
            throw new ClassCastException();
        }
    }

    public interface ContentTab {
        void setContentTab(Integer tab);
    }

}