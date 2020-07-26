package android.example.homework5fragments;

import android.content.Intent;
import android.example.homework5fragments.list.Recycler;
import android.example.homework5fragments.thread.ThreadsActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // для управления фрагментами в операции нужно получить FragmentManager
        //чтобы выполнять транзакции с фрагментами(замена,улаоение) неодходим FragmentTransaction
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //создаем сам фрагмент и выполняем необходумые транзакциии
        Recycler fragmentRecycler = new Recycler();
        fragmentTransaction.add(R.id.container, fragmentRecycler);
        fragmentTransaction.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.thread) {
            startActivity(new Intent(MainActivity.this, ThreadsActivity.class));
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
