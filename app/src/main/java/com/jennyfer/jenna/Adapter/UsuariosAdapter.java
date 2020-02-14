package com.jennyfer.jenna.Adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.jennyfer.jenna.BuzonDeCorreo.Amigos.Buscarusuario;
import com.jennyfer.jenna.BuzonDeCorreo.Amigos.ListAmigos;
import com.jennyfer.jenna.BuzonDeCorreo.Amigos.SolicitudAmigos;

public class UsuariosAdapter extends FragmentPagerAdapter {


    public UsuariosAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new ListAmigos();
        }else  if (position == 1){
            return new Buscarusuario();
        }else if (position == 2){
            return new SolicitudAmigos();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Amigos";
        }else  if (position == 1){
            return "Usuarios";
        }else if (position == 2){
            return "Solicitud";
        }
        return null;
    }

}
