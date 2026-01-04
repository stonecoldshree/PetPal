package com.yourcompany.petcare.data.local;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.yourcompany.petcare.data.model.Pet;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class PetDao_Impl implements PetDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Pet> __insertionAdapterOfPet;

  private final EntityDeletionOrUpdateAdapter<Pet> __deletionAdapterOfPet;

  private final EntityDeletionOrUpdateAdapter<Pet> __updateAdapterOfPet;

  public PetDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPet = new EntityInsertionAdapter<Pet>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `pets` (`id`,`name`,`species`,`gender`,`breed`,`ownerEmail`,`age`,`avatar`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Pet entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        if (entity.getSpecies() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getSpecies());
        }
        if (entity.getGender() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getGender());
        }
        if (entity.getBreed() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getBreed());
        }
        if (entity.getOwnerEmail() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getOwnerEmail());
        }
        statement.bindLong(7, entity.getAge());
        if (entity.getAvatar() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getAvatar());
        }
      }
    };
    this.__deletionAdapterOfPet = new EntityDeletionOrUpdateAdapter<Pet>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `pets` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Pet entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfPet = new EntityDeletionOrUpdateAdapter<Pet>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `pets` SET `id` = ?,`name` = ?,`species` = ?,`gender` = ?,`breed` = ?,`ownerEmail` = ?,`age` = ?,`avatar` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Pet entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        if (entity.getSpecies() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getSpecies());
        }
        if (entity.getGender() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getGender());
        }
        if (entity.getBreed() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getBreed());
        }
        if (entity.getOwnerEmail() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getOwnerEmail());
        }
        statement.bindLong(7, entity.getAge());
        if (entity.getAvatar() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getAvatar());
        }
        statement.bindLong(9, entity.getId());
      }
    };
  }

  @Override
  public long insertPet(final Pet pet) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfPet.insertAndReturnId(pet);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deletePet(final Pet pet) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfPet.handle(pet);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updatePet(final Pet pet) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfPet.handle(pet);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Pet> getPetsForOwner(final String ownerEmail) {
    final String _sql = "SELECT * FROM pets WHERE ownerEmail = ? ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (ownerEmail == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, ownerEmail);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfSpecies = CursorUtil.getColumnIndexOrThrow(_cursor, "species");
      final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
      final int _cursorIndexOfBreed = CursorUtil.getColumnIndexOrThrow(_cursor, "breed");
      final int _cursorIndexOfOwnerEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "ownerEmail");
      final int _cursorIndexOfAge = CursorUtil.getColumnIndexOrThrow(_cursor, "age");
      final int _cursorIndexOfAvatar = CursorUtil.getColumnIndexOrThrow(_cursor, "avatar");
      final List<Pet> _result = new ArrayList<Pet>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Pet _item;
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        final String _tmpSpecies;
        if (_cursor.isNull(_cursorIndexOfSpecies)) {
          _tmpSpecies = null;
        } else {
          _tmpSpecies = _cursor.getString(_cursorIndexOfSpecies);
        }
        final String _tmpGender;
        if (_cursor.isNull(_cursorIndexOfGender)) {
          _tmpGender = null;
        } else {
          _tmpGender = _cursor.getString(_cursorIndexOfGender);
        }
        final String _tmpBreed;
        if (_cursor.isNull(_cursorIndexOfBreed)) {
          _tmpBreed = null;
        } else {
          _tmpBreed = _cursor.getString(_cursorIndexOfBreed);
        }
        final String _tmpOwnerEmail;
        if (_cursor.isNull(_cursorIndexOfOwnerEmail)) {
          _tmpOwnerEmail = null;
        } else {
          _tmpOwnerEmail = _cursor.getString(_cursorIndexOfOwnerEmail);
        }
        final int _tmpAge;
        _tmpAge = _cursor.getInt(_cursorIndexOfAge);
        _item = new Pet(_tmpName,_tmpSpecies,_tmpGender,_tmpBreed,_tmpOwnerEmail,_tmpAge);
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpAvatar;
        if (_cursor.isNull(_cursorIndexOfAvatar)) {
          _tmpAvatar = null;
        } else {
          _tmpAvatar = _cursor.getString(_cursorIndexOfAvatar);
        }
        _item.setAvatar(_tmpAvatar);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public int countPetsForOwner(final String ownerEmail) {
    final String _sql = "SELECT COUNT(*) FROM pets WHERE ownerEmail = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (ownerEmail == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, ownerEmail);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _result;
      if (_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Pet> getAllPets() {
    final String _sql = "SELECT * FROM pets ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfSpecies = CursorUtil.getColumnIndexOrThrow(_cursor, "species");
      final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
      final int _cursorIndexOfBreed = CursorUtil.getColumnIndexOrThrow(_cursor, "breed");
      final int _cursorIndexOfOwnerEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "ownerEmail");
      final int _cursorIndexOfAge = CursorUtil.getColumnIndexOrThrow(_cursor, "age");
      final int _cursorIndexOfAvatar = CursorUtil.getColumnIndexOrThrow(_cursor, "avatar");
      final List<Pet> _result = new ArrayList<Pet>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Pet _item;
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        final String _tmpSpecies;
        if (_cursor.isNull(_cursorIndexOfSpecies)) {
          _tmpSpecies = null;
        } else {
          _tmpSpecies = _cursor.getString(_cursorIndexOfSpecies);
        }
        final String _tmpGender;
        if (_cursor.isNull(_cursorIndexOfGender)) {
          _tmpGender = null;
        } else {
          _tmpGender = _cursor.getString(_cursorIndexOfGender);
        }
        final String _tmpBreed;
        if (_cursor.isNull(_cursorIndexOfBreed)) {
          _tmpBreed = null;
        } else {
          _tmpBreed = _cursor.getString(_cursorIndexOfBreed);
        }
        final String _tmpOwnerEmail;
        if (_cursor.isNull(_cursorIndexOfOwnerEmail)) {
          _tmpOwnerEmail = null;
        } else {
          _tmpOwnerEmail = _cursor.getString(_cursorIndexOfOwnerEmail);
        }
        final int _tmpAge;
        _tmpAge = _cursor.getInt(_cursorIndexOfAge);
        _item = new Pet(_tmpName,_tmpSpecies,_tmpGender,_tmpBreed,_tmpOwnerEmail,_tmpAge);
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpAvatar;
        if (_cursor.isNull(_cursorIndexOfAvatar)) {
          _tmpAvatar = null;
        } else {
          _tmpAvatar = _cursor.getString(_cursorIndexOfAvatar);
        }
        _item.setAvatar(_tmpAvatar);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Pet getPetByIdSync(final long id) {
    final String _sql = "SELECT * FROM pets WHERE id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfSpecies = CursorUtil.getColumnIndexOrThrow(_cursor, "species");
      final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
      final int _cursorIndexOfBreed = CursorUtil.getColumnIndexOrThrow(_cursor, "breed");
      final int _cursorIndexOfOwnerEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "ownerEmail");
      final int _cursorIndexOfAge = CursorUtil.getColumnIndexOrThrow(_cursor, "age");
      final int _cursorIndexOfAvatar = CursorUtil.getColumnIndexOrThrow(_cursor, "avatar");
      final Pet _result;
      if (_cursor.moveToFirst()) {
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        final String _tmpSpecies;
        if (_cursor.isNull(_cursorIndexOfSpecies)) {
          _tmpSpecies = null;
        } else {
          _tmpSpecies = _cursor.getString(_cursorIndexOfSpecies);
        }
        final String _tmpGender;
        if (_cursor.isNull(_cursorIndexOfGender)) {
          _tmpGender = null;
        } else {
          _tmpGender = _cursor.getString(_cursorIndexOfGender);
        }
        final String _tmpBreed;
        if (_cursor.isNull(_cursorIndexOfBreed)) {
          _tmpBreed = null;
        } else {
          _tmpBreed = _cursor.getString(_cursorIndexOfBreed);
        }
        final String _tmpOwnerEmail;
        if (_cursor.isNull(_cursorIndexOfOwnerEmail)) {
          _tmpOwnerEmail = null;
        } else {
          _tmpOwnerEmail = _cursor.getString(_cursorIndexOfOwnerEmail);
        }
        final int _tmpAge;
        _tmpAge = _cursor.getInt(_cursorIndexOfAge);
        _result = new Pet(_tmpName,_tmpSpecies,_tmpGender,_tmpBreed,_tmpOwnerEmail,_tmpAge);
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _result.setId(_tmpId);
        final String _tmpAvatar;
        if (_cursor.isNull(_cursorIndexOfAvatar)) {
          _tmpAvatar = null;
        } else {
          _tmpAvatar = _cursor.getString(_cursorIndexOfAvatar);
        }
        _result.setAvatar(_tmpAvatar);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Pet getById(final long id) {
    final String _sql = "SELECT * FROM pets WHERE id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfSpecies = CursorUtil.getColumnIndexOrThrow(_cursor, "species");
      final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
      final int _cursorIndexOfBreed = CursorUtil.getColumnIndexOrThrow(_cursor, "breed");
      final int _cursorIndexOfOwnerEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "ownerEmail");
      final int _cursorIndexOfAge = CursorUtil.getColumnIndexOrThrow(_cursor, "age");
      final int _cursorIndexOfAvatar = CursorUtil.getColumnIndexOrThrow(_cursor, "avatar");
      final Pet _result;
      if (_cursor.moveToFirst()) {
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        final String _tmpSpecies;
        if (_cursor.isNull(_cursorIndexOfSpecies)) {
          _tmpSpecies = null;
        } else {
          _tmpSpecies = _cursor.getString(_cursorIndexOfSpecies);
        }
        final String _tmpGender;
        if (_cursor.isNull(_cursorIndexOfGender)) {
          _tmpGender = null;
        } else {
          _tmpGender = _cursor.getString(_cursorIndexOfGender);
        }
        final String _tmpBreed;
        if (_cursor.isNull(_cursorIndexOfBreed)) {
          _tmpBreed = null;
        } else {
          _tmpBreed = _cursor.getString(_cursorIndexOfBreed);
        }
        final String _tmpOwnerEmail;
        if (_cursor.isNull(_cursorIndexOfOwnerEmail)) {
          _tmpOwnerEmail = null;
        } else {
          _tmpOwnerEmail = _cursor.getString(_cursorIndexOfOwnerEmail);
        }
        final int _tmpAge;
        _tmpAge = _cursor.getInt(_cursorIndexOfAge);
        _result = new Pet(_tmpName,_tmpSpecies,_tmpGender,_tmpBreed,_tmpOwnerEmail,_tmpAge);
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _result.setId(_tmpId);
        final String _tmpAvatar;
        if (_cursor.isNull(_cursorIndexOfAvatar)) {
          _tmpAvatar = null;
        } else {
          _tmpAvatar = _cursor.getString(_cursorIndexOfAvatar);
        }
        _result.setAvatar(_tmpAvatar);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
