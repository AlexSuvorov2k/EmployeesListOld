package ru.alexsuvorov.employee_service.fragments;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ru.alexsuvorov.employee_service.R;
import ru.alexsuvorov.employee_service.db.DBAdapter;
import ru.alexsuvorov.employee_service.model.Worker;

public class EmployeeDetail extends Fragment {

    private TextView fname, lname, birthday, specialty, age;
    private ImageView avatarImage;
    private Worker worker;
    DBAdapter dbAdapter;
    Resources res;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.employee_detail,
                container, false);
        Bundle bundle = getArguments();
        worker = (Worker) bundle.getSerializable("worker");
        dbAdapter = new DBAdapter(this.getContext());
        fname = view.findViewById(R.id.employee_fname);
        lname = view.findViewById(R.id.employee_lname);
        birthday = view.findViewById(R.id.employee_birthday);
        specialty = view.findViewById(R.id.employee_specialty);
        age = view.findViewById(R.id.employee_age);
        avatarImage = view.findViewById(R.id.employee_image);
        res = getContext().getResources();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fname.setText(worker.getF_name());
        lname.setText(worker.getL_name());
        if (worker.getAge() != 0) {
            age.setText(String.format(res.getString(R.string.worker_age_prefix), String.valueOf(worker.getAge())));
        }
        birthday.setText(String.valueOf(worker.getBirthday()));
        specialty.setText(dbAdapter.getSpecialtyNameById(worker.getSpecialty()));
        String avatarLink = worker.getAvatarLink();
        //Не нашёл способ обрабатывать пустые ссылки
        if (avatarLink == null || avatarLink.isEmpty()) {
            Picasso.get()
                    .load(R.mipmap.ic_profile)
                    .resize(350, 350)
                    .centerCrop()
                    .into(avatarImage);
        } else {
            Picasso.get()
                    .load(avatarLink)
                    .placeholder(R.mipmap.ic_profile)
                    .resize(350, 350)
                    .centerCrop()
                    .into(avatarImage);
        }
    }
}
