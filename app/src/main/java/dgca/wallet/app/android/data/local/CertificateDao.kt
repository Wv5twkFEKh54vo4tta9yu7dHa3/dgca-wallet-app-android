/*
 *  ---license-start
 *  eu-digital-green-certificates / dgca-verifier-app-android
 *  ---
 *  Copyright (C) 2021 T-Systems International GmbH and all other contributors
 *  ---
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  ---license-end
 *
 *  Created by osarapulov on 5/10/21 10:20 PM
 */

package dgca.wallet.app.android.data.local

import androidx.room.*

@Dao
interface CertificateDao {

    @Query("SELECT * FROM certificates")
    suspend fun getAll(): List<CertificateEntity>

    @Query("SELECT * FROM certificates WHERE id LIKE :id LIMIT 1")
    suspend fun getById(id: Int): CertificateEntity?

    @Query("DELETE FROM certificates WHERE id = :id")
    suspend fun delete(id: Int): Int

    @Insert(entity = CertificateEntity::class, onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: CertificateEntity): Long

    @Update(entity = CertificateEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(item: CertificateEntity): Int

    @Transaction
    suspend fun upsert(entity: CertificateEntity) {
        val id = insert(entity)
        if (id == -1L) {
            update(entity)
        }
    }
}