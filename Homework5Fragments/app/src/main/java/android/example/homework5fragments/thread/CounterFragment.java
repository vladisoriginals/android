package android.example.homework5fragments.thread;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.example.homework5fragments.R;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 *
 */

public class CounterFragment extends Fragment {

    private static final String FRAGMENT_TYPE = "fragment_type";

    static CounterFragment newInstance(String fragmentTitle) {
        CounterFragment fragment = new CounterFragment();
        Bundle bundle = new Bundle(1);
        bundle.putString(FRAGMENT_TYPE, fragmentTitle);
        fragment.setArguments(bundle);
        return fragment;
    }

    private TaskEvent.Operation listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_activity_thread, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (getActivity() != null && getActivity() instanceof TaskEvent.Operation) {
            listener = (TaskEvent.Operation) getActivity();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button buttonCreate = view.findViewById(R.id.button_create);
        Button buttonStart = view.findViewById(R.id.button_start);
        Button buttonCancel = view.findViewById(R.id.button_cancel);

        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.createTask();
            }
        });
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.startTask();
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.cancelTask();
            }
        });

        if (getArguments()==null){
            return;
        }
        String fragmentText = this.getArguments().getString(FRAGMENT_TYPE);
        TextView textView = view.findViewById(R.id.text_value);
        textView.setText(fragmentText);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    void updateFragmentText(String text){
        TextView textView = Objects.requireNonNull(getView()).findViewById(R.id.text_value);
        textView.setText(text);
    }
}

