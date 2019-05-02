package com.sorftweb.sandino.recyclerpersonalizado.FRAGMENTOS;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sorftweb.sandino.recyclerpersonalizado.ETC.BaseVolleyFragment;
import com.sorftweb.sandino.recyclerpersonalizado.R;


public class FragmentoPerfil extends BaseVolleyFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = LayoutInflater.from( container.getContext() )
                .inflate( R.layout.fragmento_perfil , container, false );
        return view;
    }

}
