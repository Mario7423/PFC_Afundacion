package com.example.app.RecyclerView;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app.R;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewDetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView title, date, text;
    private ImageView imageView;

    public NewDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewDetailFragment newInstance(String param1, String param2) {
        NewDetailFragment fragment = new NewDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_new_detail, container, false);

        imageView = view.findViewById(R.id.image);  // Instanciado de los elementos del xml para poder cambiar su contendio
        title = view.findViewById(R.id.title);
        date = view.findViewById(R.id.date);
        text = view.findViewById(R.id.text);

        Bundle args = getArguments();  // Recuperación de la información proporcionada por HomeFragment, que contiene los datos de un jugador específico

        if(args != null && args.containsKey("news")){

            New news = (New) args.getSerializable("news");

            Picasso.get().load(news.getImage()).into(imageView);  // Cargado de los datos en los TextView y la imagen en el ImageView gracias a Picasso
            title.setText(news.getTitle());
            date.setText(news.getDate());
            text.setText(news.getText());
        }

        return view;

    }
}