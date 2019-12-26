package com.icspl.createsoc.VIewpagerEditsoc;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.icspl.createsoc.Adaptor.BlockVPAdaptor;
import com.icspl.createsoc.Model.BlockVPModel;
import com.icspl.createsoc.Model.ServerResponseModel;
import com.icspl.createsoc.R;
import com.icspl.createsoc.callback.ApiService;
import com.icspl.kartik.myapplication.utils.Common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BlockVP.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BlockVP#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlockVP extends Fragment implements BlockVPAdaptor.Blockhandler {
    View v;
    SharedPreferences pref;
    SharedPreferences.Editor edit;
    public RecyclerView rvBlock;
    public EditText et_enterblocks;
    public TextView tv_bolockenter;
    public Button btn_Savedetails;
   public BlockVPAdaptor blockVPAdaptor;
    public List<BlockVPModel> list=new ArrayList<>();;
    HashMap<Integer, String> block = new HashMap<Integer, String>();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private OnFragmentInteractionListener mListener;

    public BlockVP() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlockVP.
     */
    // TODO: Rename and change types and number of parameters
    public static BlockVP newInstance(String param1, String param2) {
        BlockVP fragment = new BlockVP();
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
        // Inflate the layout for this fragment

         v = inflater.inflate(R.layout.fragment_block_v, container, false);
        rvBlock = v.findViewById(R.id.rvBlock);
        et_enterblocks= v.findViewById(R.id.et_enterblocks);
        tv_bolockenter= v.findViewById(R.id.tv_bolockenter);
        btn_Savedetails=v.findViewById(R.id.btn_Savedetails);
        pref= getActivity().getSharedPreferences("MyPref", 0);
//list= new ArrayList<>();
et_enterblocks.addTextChangedListener(new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        list.clear();

        if(charSequence.toString().equals(""))
        {
            list.clear();
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            rvBlock.setLayoutManager(linearLayoutManager);
            blockVPAdaptor = new BlockVPAdaptor(getActivity(),list,BlockVP.this);
            rvBlock.setAdapter(blockVPAdaptor);
            Toast.makeText(getContext(), "cannot be null", Toast.LENGTH_SHORT).show();

        }
        else{
            if(et_enterblocks.getText().toString().matches("[0-9]+")) {
                int number = Integer.parseInt(et_enterblocks.getText().toString());
                list.clear();

                for (int j = 0; j < number; j++) {
                    BlockVPModel blockVPModel = new BlockVPModel();
                    list.add(blockVPModel);

                }
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                rvBlock.setLayoutManager(linearLayoutManager);
                blockVPAdaptor = new BlockVPAdaptor(getActivity(), list, BlockVP.this);
                rvBlock.setAdapter(blockVPAdaptor);
            }
            else {
                Toast.makeText(getActivity(),"Please enter valid number",Toast.LENGTH_LONG).show();
            }

        }

    }

    @Override
    public void afterTextChanged(Editable editable) {


    }
});
        btn_Savedetails.setOnClickListener(new View.OnClickListener() {
            int number=0;
            @Override
            public void onClick(View v) {
                if(!et_enterblocks.getText().toString().equals("")) {
                    number = Integer.parseInt(et_enterblocks.getText().toString());
                    if(block.size()!=number)
                    {
                        Toast.makeText(getActivity(),"Please fill All values",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    for (int i = 1; i <=number ; i++) {
                        Common common = new Common();
                        common.getAPI().addblocks(pref.getString("S_id" +
                                "",""),block.get(i)).
                        enqueue(new Callback<List<ServerResponseModel>>() {
                            @Override
                            public void onResponse(Call<List<ServerResponseModel>> call, Response<List<ServerResponseModel>> response) {
                                if(response.isSuccessful()&&response.body().get(0).getServerResponse().equals(1))
                                {
                                    Toast.makeText(getContext(),"Block added successfully",Toast.LENGTH_LONG).show();
                                }
                                else {
                                    Toast.makeText(getContext(),"Failed to add Block",Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<List<ServerResponseModel>> call, Throwable t) {
                                Toast.makeText(getContext(),"No internet ",Toast.LENGTH_LONG).show();

                            }
                        });
                    }
                    Toast.makeText(getActivity(),"Blocks successfully added",Toast.LENGTH_LONG).show();

                }
                else
                {
                    Toast.makeText(getActivity(),"Enter number of blocks",Toast.LENGTH_LONG).show();
                }
            }
        });
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void blocknamecallback(Integer pos, String blockName) {
        if(blockName.equals(""))
        {
           if(block.containsKey(pos+1))
           {
               block.remove(pos+1);
           }
        }
        else
        {
            block.put(pos+1,blockName);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
